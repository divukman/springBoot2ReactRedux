package com.dimitar.ppmtool.security;

import com.dimitar.ppmtool.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
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
}
