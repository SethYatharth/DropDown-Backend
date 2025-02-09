package com.dropdown.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.expiry.access-token}")
    private Long accessExpiry;
    @Value("${jwt.expiry.refresh-token}")
    private Long refreshExpiry;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY ;


    public String extractEmail(String jwtToken) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    public String extractRole(String jwtToken) {
        return extractClaims(jwtToken, claims -> claims.get("role", String.class));
    }

    public <T> T extractClaims(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","))
        );
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims()
                .add(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiry))
                .and()
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .claims()
                .add(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiry))
                .and()
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username = extractClaims(token, Claims::getSubject);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","))
        );
        return generateRefreshToken(claims, userDetails);
    }
}

//package com.dropdown.config;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Service
//public class JwtService {
//
//    private final Long accessExpiry;
//    private final Long refreshExpiry;
//    private final PrivateKey privateKey;
//    private final PublicKey publicKey;
//
//    public JwtService(
//            @Value("${jwt.expiry.refresh-token}") Long refreshExpiry,
//            @Value("${jwt.expiry.access-token}") Long accessExpiry,
//            @Value("${jwt.rsa.private-key}") String privateKeyBase64,
//            @Value("${jwt.rsa.public-key}") String publicKeyBase64) {
//        this.privateKey = loadPrivateKey(privateKeyBase64);
//        this.publicKey = loadPublicKey(publicKeyBase64);
//        this.accessExpiry = accessExpiry;
//        this.refreshExpiry = refreshExpiry;
//    }
//
//    private PrivateKey loadPrivateKey(String base64Key) {
//        try {
//            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
//            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePrivate(spec);
//        } catch (Exception e) {
//            throw new IllegalStateException("Failed to load private key", e);
//        }
//    }
//
//    private PublicKey loadPublicKey(String base64Key) {
//        try {
//            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(spec);
//        } catch (Exception e) {
//            throw new IllegalStateException("Failed to load public key", e);
//        }
//    }
//
//    public String extractEmail(String jwtToken) {
//        return extractClaims(jwtToken, Claims::getSubject);
//    }
//
//    public String extractRole(String jwtToken) {
//        return extractClaims(jwtToken, claims -> claims.get("role", String.class));
//    }
//
//    public <T> T extractClaims(String jwtToken, Function<Claims, T> claimResolver) {
//        final Claims claims = extractAllClaims(jwtToken);
//        return claimResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String jwtToken) {
//        return Jwts
//                .parser()
//                .verifyWith(publicKey)
//                .build()
//                .parseSignedClaims(jwtToken)
//                .getPayload();
//    }
//
//
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", userDetails.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","))
//        );
//        return generateToken(claims, userDetails);
//    }
//
//    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts
//                .builder()
//                .claims()
//                .add(extraClaims)
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + accessExpiry))
//                .and()
//                .signWith(privateKey, Jwts.SIG.RS256)
//                .compact();
//    }
//
//    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
//        return Jwts
//                .builder()
//                .claims()
//                .add(extraClaims)
//                .subject(userDetails.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + refreshExpiry))
//                .and()
//                .signWith(privateKey, Jwts.SIG.RS256)
//                .compact();
//    }
//
//    public boolean isTokenValid(String token,UserDetails userDetails) {
//        final String username = extractClaims(token, Claims::getSubject);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//    private Date extractExpiration(String token) {
//        return extractClaims(token, Claims::getExpiration);
//    }
//
//
//    public String generateRefreshToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", userDetails.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","))
//        );
//        return generateRefreshToken(claims, userDetails);
//    }
//}
