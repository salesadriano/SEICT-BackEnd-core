package br.com.lamppit.core.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.core.util.JwtUtils;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtUtils.getToken(request);

        if (token != null && jwtUtils.validateToken(token)) {
            User user = null;

            try {
                user = jwtUtils.extractUserLoginDto(token);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (user != null) {
                List<GrantedAuthority> userAuthorities = new ArrayList<>();
                userAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        token,
                        userAuthorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}
