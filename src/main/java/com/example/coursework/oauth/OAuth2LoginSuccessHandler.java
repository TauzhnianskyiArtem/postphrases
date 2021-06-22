package com.example.coursework.oauth;

import com.example.coursework.domain.Role;
import com.example.coursework.domain.User;
import com.example.coursework.service.interf.UserService;
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
        String email = principal.getEmail();
        Optional<User> userByDB = userService.findByEmail(email);
        if (!userByDB.isPresent()) {

            String username = principal.getName();

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userService.save(user);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
