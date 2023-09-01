package com.asignment.app.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
@RestController
public class JWTController {
    static Logger log = Logger.getLogger(JWTController.class.getName());
    public static String currentToken =null;

    public static String getCurrentToken() {
        return currentToken;
    }

    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    /**We are generating token here using generate token
     then we are authenticating the username and password
     if the authentication is successful try block will run else catch block will come in existence
     */
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        log.info("JwtRequest "+jwtRequest);
        //Authenticating the username and password
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }catch(BadCredentialsException e){

            e.printStackTrace();
            throw new Exception("Bad Credential");
        }
        //
        UserDetails userDetails =customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        String token= jwtUtil.generateToken(userDetails);
        log.info("Jwt "+ token);
        log.warning("This token is not valid for long time");
        currentToken = token;
        return ResponseEntity.ok(new JwtResponse(token));
    }
}