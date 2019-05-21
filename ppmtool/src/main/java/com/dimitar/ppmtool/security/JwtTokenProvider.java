package com.dimitar.ppmtool.security;

import com.dimitar.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.jdbc.datasource.IsolationLevelDataSourceAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public String generateToken(final Authentication authentication) {
        final User user = (User)authentication.getPrincipal();
        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);
        final String userId = Long.toString(user.getId());

        final Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Object)(userId));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(final String token) {
        boolean result = false;
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            result = true;
        }
        catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch(IllegalArgumentException ex) {
            System.out.println("JWT claims is empty");
        }

        return result;
    }

    public Long getUserIdFromJWT(final String token) {
        final Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong((String)claims.get("id"));
    }
}
