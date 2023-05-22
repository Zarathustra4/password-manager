package com.passpnu.passwordmanager.filter;


import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.entity.Role;
import com.passpnu.passwordmanager.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final var authHeader = httpServletRequest.getHeader("Authorization");

        String jwt = null;

        String username = null;
        String encryptionKey = null;
        Role role = null;
        Long id = null;
        final String headerStart = "Bearer ";


        if (authHeader != null && authHeader.startsWith(headerStart)) {
            jwt = authHeader.substring(headerStart.length());
            Claims claims = jwtUtil.extractAllClaims(jwt);

            username = jwtUtil.extractUsername(jwt);
            role = Role.valueOf( (String) claims.get("role") );
            encryptionKey = (String) claims.get("encryptionKey");
            id = Long.valueOf( (Integer) claims.get("id") );
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthUserDetailsDto userDetails = AuthUserDetailsDto.builder()
                    .username(username)
                    .role(role)
                    .encryptionKey(encryptionKey)
                    .id(id)
                    .build();

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
