package com.example.electroscoot.services;

import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.interfaces.IJwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
public class JwtService implements IJwtService {
    private final Clock clock;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.exp.hours}")
    private int expInHours;

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;
        return Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals("jwt"))
            .map(Cookie::getValue)
            .findAny().orElse(null);
    }

    @Override
    public String generateJWT(User user) {

        return Jwts
                .builder()
                .setClaims(new HashMap<>(Map.of("authorities", mapRolesToAuthorities(user.getRoles()))))
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now(clock)))
                .setExpiration(Date.from(Instant.now(clock).plusSeconds(expInHours * 3600L)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateJWT(String jwt) {
        return extractExpiration(jwt).after(Date.from(Instant.now(clock)));
    }

    @Override
    public Authentication getAuthentication(String jwt) {
        String username = extractUsername(jwt);
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(jwt);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public String extractUsername(String jwt) {
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

    public Date extractExpiration(String jwt) {
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

    private Set<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> result = new HashSet<>();

        for (Role role : roles) {
            result.addAll(role.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toSet()));
        }

        return result;
    }
}
