package com.websocket.websocket.models;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtToken implements Serializable {

    public final long expiredTime = 60*60; //This value is expressed in minutes

    @Value("jwt.secert")
    public String secretKey;

    public String getUsername(String token){
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> function) {
        final Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public boolean isExpired(String token){
        final Date expired = getExpiredDate(token);
        return expired.before(new Date());
    }

    public Date getExpiredDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String createToken(UserDetails user){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public boolean validToken(String token, UserDetails user){
        final String username = getUsername(token);
        return username.equals(user.getUsername()) && !isExpired(token);
    }

}
