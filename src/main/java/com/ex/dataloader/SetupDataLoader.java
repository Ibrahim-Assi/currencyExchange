package com.ex.dataloader;

import com.ex.common.constants.AppConstants;
import com.ex.dataloader.excelReader.BankBranchesExcelReader;
import com.ex.dataloader.excelReader.BankExcelReader;
import com.ex.models.entities.gl.GlCurrencies;
import com.ex.models.entities.gl.GlCurrencyRates;
import com.ex.models.entities.gl.GlCurrencyRatesPK;
import com.ex.repositories.gl.GlCurrenciesRepository;
import com.ex.repositories.gl.GlCurrencyRatesRepository;
import com.ex.security.model.entities.Privilege;
import com.ex.security.model.entities.Role;
import com.ex.security.model.entities.User;
import com.ex.security.repositories.PrivilegeRepository;
import com.ex.security.repositories.RoleRepository;
import com.ex.security.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class  SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final GlCurrenciesRepository glCurrenciesRepository;
    private final GlCurrencyRatesRepository glCurrencyRatesRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankExcelReader bankExcelReader;
    private final BankBranchesExcelReader bankBranchesExcelReader;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE", "صلاحية استعلام");
        Privilege addPrivilege = createPrivilegeIfNotFound("CREATE_PRIVILEGE", "صلاحية اضافة");
        Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE", "صلاحية تعديل");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE", "صلاحية حذف");
        Privilege postVouchPrivilege = createPrivilegeIfNotFound("POST_VOUCH_PRIVILEGE", "صلاحية ترحيل السندات");
        Privilege cancelVouchPrivilege = createPrivilegeIfNotFound("CANCEL_VOUCH_PRIVILEGE", "صلاحية الغاء السندات");

        List<Privilege> employeePrivileges = Arrays.asList(readPrivilege, addPrivilege, postVouchPrivilege);
        createRoleIfNotFound(AppConstants.ACCOUNTANT_ROLE, "محاسب", employeePrivileges);
        createRoleIfNotFound(AppConstants.ADMIN_ROLE, "مدير النظام", null);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        if (userRepository.findByEmail("test@test.com").isEmpty()) {
            User user = new User();
            user.setFirstName("مدير النظام");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setRoles(Collections.singleton(adminRole));
            user.setEnabled(true);
            user.setRequirePwdChange(false);

            userRepository.save(user);
        }else {
            User user = userRepository.findByEmail("test@test.com").get();
            user.setEnabled(true);
            user.setRequirePwdChange(true);
            userRepository.save(user);
        }

        // Initialize currency ...............
        GlCurrencies dinar = new GlCurrencies("دينار أردني", "Jordanian Dinar", "JOD", "Qirsh", "قرش", 1.0, 1.0);
        GlCurrencies shekel = new GlCurrencies("شيكل", "Israeli Shekel", "ILS", "Agora", "اغورة", 1.0, 1.0);
        GlCurrencies dollar = new GlCurrencies("دولار أمريكي", "US Dollar", "USD", "cent", "سنت", 1.0, 1.0);
        GlCurrencies euro = new GlCurrencies("يورو", "Euro", "EUR", "cent", "سنت", 1.0, 1.0);
        glCurrenciesRepository.save(dinar);
        glCurrenciesRepository.save(shekel);
        glCurrenciesRepository.save(dollar);
        glCurrenciesRepository.save(euro);

        // Initialize CurrencyRates ...............
        GlCurrencyRates dollarToJod = new GlCurrencyRates(new GlCurrencyRatesPK("JOD", "ILS", new Date()), 5.20, 5.18, 5.195);
        GlCurrencyRates euroToJod = new GlCurrencyRates(new GlCurrencyRatesPK("USD", "ILS", new Date()), 3.688, 3.679, 3.683);
        GlCurrencyRates shekelToJod = new GlCurrencyRates(new GlCurrencyRatesPK("EUR", "ILS", new Date()), 4.055, 4.043, 4.049);
        glCurrencyRatesRepository.save(dollarToJod);
        glCurrencyRatesRepository.save(euroToJod);
        glCurrencyRatesRepository.save(shekelToJod);


        alreadySetup = true;
    }

    @Transactional
    public Privilege createPrivilegeIfNotFound(String name, String description) {

        Privilege privilege = privilegeRepository.findByName(name).orElse(null);
        if (privilege == null) {
            privilege = new Privilege(name, description);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    public void createRoleIfNotFound(String name, String description, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name, description, privileges);
            roleRepository.save(role);
        }
    }

    @PostConstruct
    public void init() throws IOException {
        bankExcelReader.readExcel();
        bankBranchesExcelReader.readExcel();
    }

}
