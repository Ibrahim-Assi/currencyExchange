package com.ex.security.services;

import com.ex.common.tools.ValidateTool;
import com.ex.exceptions.ServiceException;
import com.ex.exceptions.ServiceResponse;
import com.ex.exceptions.ServiceResult;
import com.ex.security.model.dto.UserDTO;
import com.ex.security.model.entities.Role;
import com.ex.security.model.entities.User;
import com.ex.security.repositories.RoleRepository;
import com.ex.security.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ex.common.constants.AppConstants.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public ServiceResponse createUser(@NotNull UserDTO userDTO, List<String> roleIds, List<String> centerIds) {


        User user = new User();
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new ServiceException(String.format("خطأ!! اسم المستخدم:%s موجود", userDTO.getEmail()));
        if (!Objects.equals(userDTO.getNewPassword(), userDTO.getNewPasswordMatch())) {
            throw new ServiceException("كلمة المرور غير متطابقة");
        }
        try {
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setEnabled(userDTO.getEnabled());
            user.setRequirePwdChange(userDTO.getRequirePwdChange());
            user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
            setUserRoles(user, roleIds);
            userRepository.save(user);
            return new ServiceResult(SUCCESS_INSERT_MSG);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> usersList = userRepository.findAll();
        if (ValidateTool.isEmpty(usersList))
            throw new ServiceException(NO_DATA_EXCEPTION_MSG);

        return usersList.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(@NotNull Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(String.format("user with ID:(%s) Not Found !.", userId)));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(@NotNull String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceException(String.format("user with email:(%s) Not Found !.", email)));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public ServiceResponse updateUser(@NotNull UserDTO userDTO, List<String> roleIds, List<String> centerIds) {
        Optional<User> userObject = userRepository.findById(userDTO.getUserId());
        if (userObject.isEmpty()) {
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
        User user = userObject.get();
        if (!Objects.equals(userDTO.getNewPassword(), userDTO.getNewPasswordMatch()))
            throw new ServiceException("كلمة المرور غير متطابقة");
        try {
            user.setEnabled(userDTO.getEnabled());
            user.setRequirePwdChange(userDTO.getRequirePwdChange());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            setUserRoles(user, roleIds);
            user.setPassword(passwordEncoder.encode(userDTO.getNewPassword().trim()));
            userRepository.save(user);
            return new ServiceResult(SUCCESS_UPDATE_MSG);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
    }

    @Override
    public ServiceResponse changeUserPassword(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new ServiceException(GLOBAL_EXCEPTION_MSG));

        try {
            validatePassword(user, userDTO);
        } catch (ServiceException exception) {
            return exception;
        }
        String encodedPass = passwordEncoder.encode(userDTO.getNewPassword());
        user.setPassword(encodedPass);
        user.setRequirePwdChange(false); // to prevent redirect to change password page after success login

        try {
            userRepository.save(user);
            return new ServiceResult(PASSWORD_CHANGED_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }
    }

    @Override
    public ServiceResponse deleteUser(@NotNull Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(String.format("user with ID:(%s) Not Found !.", userId)));
        try {
            userRepository.delete(user);
            return new ServiceResult(SUCCESS_DELETE_MSG);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(GLOBAL_EXCEPTION_MSG);
        }

    }

    private void setUserRoles(User user, List<String> roleIds) {
        user.setRoles(null);
        List<Role> roles = new ArrayList<>();
        roleIds.forEach(id -> {
            Role role = roleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ServiceException(GLOBAL_EXCEPTION_MSG + " (trying to assign not exists role.) "));
            if (role != null)
                roles.add(role);
        });
        if (!roles.isEmpty()) {
            user.setRoles(roles);
        }
    }


    private void validatePassword(User user, UserDTO userDTO) {
        String encodedCurrentPass = user.getPassword();
        String oldPass = userDTO.getOldPassword().trim();
        String newPass = userDTO.getNewPassword().trim();
        String newPassMatch = userDTO.getNewPasswordMatch().trim();

        if (!ValidateTool.isEmpty(userDTO.getOldPassword()) && !ValidateTool.isEmpty(userDTO.getNewPassword()) && !ValidateTool.isEmpty(userDTO.getNewPasswordMatch())) {
            if (!passwordEncoder.matches(oldPass, encodedCurrentPass))
                throw new ServiceException(INCORRECT_OLD_PASSWORD);

            if (passwordEncoder.matches(newPass, encodedCurrentPass))
                throw new ServiceException(INCORRECT_REUSE_OLD_PASSWORD);

            if (!newPass.equals(newPassMatch))
                throw new ServiceException(PASSWORD_NOT_MATCH);

        } else throw new ServiceException(GLOBAL_EXCEPTION_MSG);
    }
}

