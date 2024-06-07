package com.ex.security.services;

import com.ex.security.configuration.UserInfoConfig;
import com.ex.security.model.entities.Role;
import com.ex.security.model.entities.User;
import com.ex.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        UserInfoConfig userInfoConfig = null;
        Collection<Role> roleCollection = new ArrayList<>();
        if (user.isPresent()) {
                roleCollection.addAll(user.get().getRoles());
                userInfoConfig = new UserInfoConfig(user.get(), roleCollection);
        }


        return userInfoConfig;
    }
}
