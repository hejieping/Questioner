package com.sitp.questioner.util;

import com.sitp.questioner.jwt.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by qi on 2017/7/23.
 */
@Component
public class JwtTokenUtil implements Serializable{
    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "create";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration ;

    public String getUserNameFromToken(String token)
    {
        String username = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }
        catch (Exception e)
        {
        }
        return username;
    }
    public LocalDateTime getCreatedDataFromToken(String token)
    {
        LocalDateTime created = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            Long timestamp = (Long)claims.get(CLAIM_KEY_CREATED);
            created = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                    TimeZone.getDefault().toZoneId());
        }
        catch (Exception e)
        {
            created = null;
        }
        return created;
    }

    public LocalDateTime getExpirationDateTimeFromToken(String token)
    {
        LocalDateTime expirationDateTime = null;
        try{
            final Claims claims = getClaimsFromToken(token);
            expirationDateTime = LocalDateTime.ofInstant(
                                    claims.getExpiration().toInstant(),
                                    TimeZone.getDefault().toZoneId());
        }
        catch (Exception e)
        {

        }
        return expirationDateTime;
    }

    private Claims getClaimsFromToken(String token)
    {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e)
        {
            claims = null;
        }
        return claims;
    }

    private LocalDateTime generateExpirationDate()
    {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + expiration * 1000),
                TimeZone.getDefault().toZoneId());
    }

    private Boolean isTokenExpired(String token)
    {
        final LocalDateTime expiration = getExpirationDateTimeFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }
    private Boolean isCreatedBeforeLastPasswordReset(LocalDateTime created,
                                                     LocalDateTime lastPasswordRest)
    {
        return (lastPasswordRest !=null && created.isBefore(lastPasswordRest));
    }

    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,LocalDateTime.now());
        return generateToken(claims);
    }

    String generateToken(Map<String,Object> claims){
        Instant instant = generateExpirationDate().atZone(ZoneId.systemDefault())
                .toInstant();
        Date expirationDate = Date.from(instant);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token,LocalDateTime lastPasswordReset){
        final LocalDateTime created = getCreatedDataFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created,lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token){
        String refreshToken = null;
        try{
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED,LocalDateTime.now());
            refreshToken = generateToken(claims);
        }
        catch (Exception e)
        {
        }
        return refreshToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        JwtUser user = (JwtUser) userDetails;
        final String username = getUserNameFromToken(token);
        return username.equals(user.getUsername())
                && !isTokenExpired(token);
    }
}








