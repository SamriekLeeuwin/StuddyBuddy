package com.studdybudy.service;

import com.studdybudy.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class JwtService {

    private final String issuer;
    private final Duration ttl;
    private final JwtEncoder jwtEncoder;

    public String generateToken(final User user) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(user.getUsername())
                .issuer(issuer)
                .claim("roles", List.of("ROLE_" + user.getRole().name()))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(ttl))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }
}
