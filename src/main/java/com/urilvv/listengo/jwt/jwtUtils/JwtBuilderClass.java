package com.urilvv.listengo.jwt.jwtUtils;

import com.urilvv.listengo.dto.UserDto;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtBuilderClass {

    private final String secretKey = "BQCT1sqNTOv2vBmPOUIpZqxioUgCLL41COotZ7CfioQNx9dBpuWH9ftTmI";
    public final JwtParser jwtParser;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtBuilderClass() {
        this.jwtParser = Jwts.parser().setSigningKey(secretKey).build();
    }

    public String createToken(UserDto userDto) {
        Claims claims = Jwts.claims().setSubject(userDto.getUserId())
                .add("username", userDto.getUserName())
                .add("email", userDto.getEmail())
                .build();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime modifiedDateTime = localDateTime.plusHours(1);
        Date tokenValidityTime = Date.from(
                modifiedDateTime.atZone(ZoneId.systemDefault())
                        .toInstant());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidityTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getUserId(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

}
