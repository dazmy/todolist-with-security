package com.dazmy.todolist.security.filter;

import com.dazmy.todolist.security.service.JwtService;
import com.dazmy.todolist.security.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                username = jwtService.extractUsername(token);
            }

            if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            String result = """
                    {
                        "status": 401,
                        "data": null,
                        "errors": "Unauthorized"
                    }
                    """;
            response.getWriter().println(result);
        }
    }
}
