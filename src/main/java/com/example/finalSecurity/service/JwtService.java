package com.example.finalSecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;


import javax.security.auth.Subject;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generateToken(String name) {
        Map<String, Object> claims=new HashMap<>();

        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000*60*3))
                .signWith(SIGNING_KEY).compact();

    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsTResolver){
        final Claims claims= extractAllClaims(token);
        return claimsTResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build().parseClaimsJwt(token).getBody();
    }


    public boolean valiadteToken(String token, UserDetails userDetails) {
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserName(String token) {
        return extractClaims(token,Claims::getSubject);
    }
    public boolean isTokenExpired(String token){
        return expirationDate(token).before(new Date());
    }

    public Date expirationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }
}
