package com.example.taskapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey key;
    private static final long EXPIRE_MINUTES = 30;

    public JwtService(@Value("${app.jwt.secret}") String key) {
        // Keys.hmacShaKeyFor(...) でkeyオブジェクトに変換する
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createToken(UUID userId) {

        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(EXPIRE_MINUTES));

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key)
                .compact();
    }

    public UUID parseUserId(String token) {

        try{

            String sub = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return UUID.fromString(sub);

        } catch (Exception e){
            return null;  // あとでもっとエラー内容詳細に書き直す
        }
    }

}
