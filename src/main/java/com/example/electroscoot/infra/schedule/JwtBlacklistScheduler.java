package com.example.electroscoot.infra.schedule;

import com.example.electroscoot.services.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class JwtBlacklistScheduler {
    @Autowired
    private Clock clock;
    @Value("jwt.secret")
    private String secret;
    private final Set<String> jwtBlacklist = new HashSet<>();

    public boolean blacklistJwt(String jwt) {
        return jwtBlacklist.add(jwt);
    }

    public boolean isJwtBlacklisted(String jwt) {
        return jwtBlacklist.contains(jwt);
    }

    @Scheduled(fixedDelayString = "${jwt.exp.hours}", timeUnit = TimeUnit.HOURS)
    public void refreshBlacklist() {
        jwtBlacklist.removeIf(jwt -> extractJwtExpiration(jwt).before(Date.from(Instant.now(clock))));
    }

    private Date extractJwtExpiration(String jwt) {
        return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getExpiration();
    }

    private Key getSigningKey() {
        byte[] decodedBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedBytes);
    }
}
