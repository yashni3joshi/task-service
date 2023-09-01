package com.asignment.app.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
   /*     get jwt,Bearer,validate
         This JWTAuthenticationFilter will intercept the incoming request and check if it contains a JSON Web Token(JWT).
        If JWT is present it will then call the validate method of the JWTUtil class to validate the token.
        If the validation is successful, then it will create a User Object using the JWT payload
        and store this object in the Security context, which indicates that the current user is authenticated.
      */
        String requestTokenHeader= request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;
//        null and format checking
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken=requestTokenHeader.substring(7);
            try {
                username= this.jwtUtil.extractUsername(jwtToken);
            }catch (Exception e){
                e.printStackTrace();
            }
            UserDetails userDetails=this.customUserDetailService.loadUserByUsername(username);
            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else
            {
                System.out.println("Token is Validated");
            }
        }
        filterChain.doFilter(request,response);
    }
}
