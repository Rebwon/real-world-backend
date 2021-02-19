package com.rebwon.realworldbackend.infra.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory {

  @Value("${token.expiration}")
  private Integer expirationTime;
  @Value("${token.signKey}")
  private String signKey;

  public String create(String email) {
    LocalDateTime currentTime = LocalDateTime.now();
    return Jwts.builder()
        .setSubject(email)
        .setIssuer("rebwon")
        .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
        .setExpiration(Date.from(currentTime
            .plusMinutes(expirationTime)
            .atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(SignatureAlgorithm.HS512, signKey)
        .compact();
  }
}
