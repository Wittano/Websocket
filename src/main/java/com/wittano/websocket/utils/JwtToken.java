package com.wittano.websocket.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtToken implements Serializable {

    public final long expiredTime = 60 * 60; //This value is expressed in seconds

    @Value("jwt.secert")
    public String secretKey;

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> function) {
        return function.apply(getAllClaims(token));
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token) {
        return getExpiredDate(token).before(new Date());
    }

    public Date getExpiredDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String createToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public boolean validToken(String token, String username) {
        return getUsername(token).equals(username) && !isExpired(token);
    }
}
