package com.example.coursework.userdaetails;

import com.example.coursework.domain.User;
import com.example.coursework.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class MyUserDetails implements UserDetails, OAuth2User {
    @Autowired
    private UserService userService;

    private User user;

    public MyUserDetails(User user) {
        this.user= user;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
        return this.user.isActive();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    public String getEmail(){
        return user.getEmail();
    }
}
