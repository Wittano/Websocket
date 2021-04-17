package com.wittano.websocket.filter;

import com.wittano.websocket.utils.JwtToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService service;
    private final JwtToken jwtToken;

    public JwtFilter(@Qualifier("userDetailService") UserDetailsService service, JwtToken jwtToken) {
        this.service = service;
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String requestToken = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            username = jwtToken.getUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = service.loadUserByUsername(username);

            if (jwtToken.validToken(token, userDetails.getUsername())) {
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        userDetails.getUsername(),
                                        userDetails.getPassword(),
                                        userDetails.getAuthorities()));

            }
        }

        filterChain.doFilter(request, response);
    }
}
