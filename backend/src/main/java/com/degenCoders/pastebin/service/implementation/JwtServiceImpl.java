package com.degenCoders.pastebin.service.implementation;

import com.degenCoders.pastebin.models.UserEntity;
import com.degenCoders.pastebin.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${token.expiration}")
    private int EXPIRATION_TOKEN;


    @Override
    public String extractData(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getAudience);
    }

    @Override
    public String extractUserID(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    @Override
    public String generateToken(UserEntity userDetails) {
        return generateToken(new HashMap<String, String>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, String name) {
        final String userName = extractData(token);
        return (userName.equals(name)) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, String> extraClaims, UserEntity userDetails) {
        try{
            return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                    .setAudience(userDetails.getEmail())
                    .setIssuer(userDetails.getUserId())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
        }catch (Exception e){
            throw new JwtException("Error in generate token: [ " + e.getMessage() + " ]");
        }

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}