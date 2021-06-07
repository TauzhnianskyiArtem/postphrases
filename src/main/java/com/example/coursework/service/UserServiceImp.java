package com.example.coursework.service;

import com.example.coursework.domain.User;
import com.example.coursework.repos.UserRepo;
import com.example.coursework.userdaetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UserServiceImp")
public class UserServiceImp implements UserDetailsService, UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        user.orElseThrow( () -> new  UsernameNotFoundException("User " + username + " dont exist!"));

        return user.map(MyUserDetails::new).get();

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void add(User user) {
        userRepo.save(user);
    }
}
