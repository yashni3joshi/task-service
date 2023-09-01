package com.asignment.app.jwt;

import com.asignment.app.dao.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Component
@Slf4j
public class TokenValidator extends OncePerRequestFilter {
    private final RequestMatcher generateTokenRequestMatcher = new AntPathRequestMatcher("/generate-token");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("TokenValidator -> doFilterInternal()");
        if (generateTokenRequestMatcher.matches(request)) {
            // Allow /generate-token API without token validation
            filterChain.doFilter(request, response);
            log.info("TokenValidator -> doFilterInternal() -> /generate-token");
            return;
        }
        log.info("TokenValidator -> doFilterInternal() -> /");

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && (JWTController.getCurrentToken()!=null)) {
            String token = authorizationHeader.substring(7);
            if (token.matches(JWTController.getCurrentToken())) {
                // Token is valid, proceed to the next filter or API endpoint
                filterChain.doFilter(request, response);
                log.info("TokenValidator -> doFilterInternal() -> "+request.getRequestURI());
                return;
            }
            Response errorResponse = new Response();
            errorResponse.setMsg("Authentication failed, Not a valid Token...");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            return;
        }
        // Token is invalid or missing, send an unauthorized response
        Response errorResponse = new Response();
        errorResponse.setMsg("Authentication failed, Login First");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
