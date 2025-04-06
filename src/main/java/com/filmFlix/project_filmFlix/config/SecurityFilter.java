package com.filmFlix.project_filmFlix.config;

import com.filmFlix.project_filmFlix.services.JwtService;
import com.filmFlix.project_filmFlix.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService service;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);

        if(token != null) {
            var subject = service.getSubject(token);
            var user = userService.loadUserByUsername(subject);

            var authenticate = UsernamePasswordAuthenticationToken.authenticated(
                    user, null, user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        };



        filterChain.doFilter(request, response);


    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null){
            return token.substring(7);
        }
        return null;

    }
}
