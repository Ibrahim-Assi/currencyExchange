package com.ex.security.configuration;

import com.ex.common.combo.ComboDTO;
import com.ex.models.dto.gl.GlCurrenciesDTO;
import com.ex.security.model.entities.Privilege;
import com.ex.security.model.entities.Role;
import com.ex.security.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserInfoConfig implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long   id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private Boolean requirePwdChange;
    private String selectedIntervalId;
    private List<GrantedAuthority>  authorities;
    private GlCurrenciesDTO         mainCurrency;
    private Collection<ComboDTO>    userCentersList;

    public UserInfoConfig(User user, Collection<Role> roles) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.enabled = user.getEnabled();
        this.requirePwdChange = user.getRequirePwdChange();
        this.authorities = getGrantedAuthorities(getPrivileges(roles));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
