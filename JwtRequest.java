package com.asignment.app.jwt;



import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtRequest {
    String username;
    String password;
}
