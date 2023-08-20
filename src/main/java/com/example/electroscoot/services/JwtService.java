package com.example.electroscoot.services;

import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.exp.hours}")
    private int expInHours;
    @Autowired
    private Clock clock;

    public String getTokenFromRequest(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            return null;
        }

        return auth.substring(7);
    }

    public String generateJWT(User user) {
        String jwt = Jwts
                .builder()
                .setSubject(user.getUsername())
                .setClaims(new HashMap<>(Map.of("authorities", mapRolesToAuthorities(user.getRoles()))))
                .setIssuedAt(Date.from(Instant.now(clock)))
                .setExpiration(Date.from(Instant.now(clock).plusSeconds(expInHours * 3600)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public boolean validateJWT(String jwt) {
        return extractExpiration(jwt).after(Date.from(Instant.now(clock)));
    }

    public Authentication getAuthentication(String jwt) {
        String username = extractUsername(jwt);
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(jwt);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public List<GrantedAuthority> extractAuthorities(String jwt) {
        Claims claims = extractClaims(jwt);
        List<String> authorities = convertAuthoritiesToStringList(claims);
        return AuthorityUtils.createAuthorityList(authorities);
    }

    private List<String> convertAuthoritiesToStringList(Claims claims) {
        return ((List<LinkedHashMap<String, String>>) claims.get("authorities"))
                .stream().map(authorityMap -> authorityMap.get("authority")).collect(Collectors.toList());
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] decodedBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decodedBytes);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map((role -> new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toList());
    }
}
