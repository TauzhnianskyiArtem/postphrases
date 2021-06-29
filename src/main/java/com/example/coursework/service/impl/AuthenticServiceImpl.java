package com.example.coursework.service.impl;

import com.example.coursework.domain.Role;
import com.example.coursework.domain.User;
import com.example.coursework.repos.UserRepo;
import com.example.coursework.service.interf.AuthenticService;
import com.example.coursework.userdaetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AuthenticServiceImpl extends DefaultOAuth2UserService implements AuthenticService  {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return user.map(MyUserDetails::new).get();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User auth2User =  super.loadUser(userRequest);
        String email = auth2User.getAttribute("email");
        Optional<User> userByDB = userRepo.findByEmail(email);
        if (!userByDB.isPresent()) {

            String username = auth2User.getAttribute("name");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
            return new MyUserDetails(user);
        }

        return userByDB.map(MyUserDetails::new).get();

    }

}
