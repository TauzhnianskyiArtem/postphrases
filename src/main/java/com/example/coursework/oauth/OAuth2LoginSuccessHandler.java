package com.example.coursework.oauth;

import com.example.coursework.domain.Role;
import com.example.coursework.domain.User;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserOAuth2User principal = (UserOAuth2User) authentication.getPrincipal();
        String username = principal.getName();
        Optional<User> userByDB = userService.findByUsername(username);
        if (!userByDB.isPresent()){
            User user = new User();
            user.setUsername(username);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userService.save(user);
        } else {
            User user = userByDB.get();
            user.setUsername(username);
            userService.save(user);
        }

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
