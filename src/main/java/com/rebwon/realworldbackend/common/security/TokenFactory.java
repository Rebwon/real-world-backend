package com.rebwon.realworldbackend.common.security;

import com.rebwon.realworldbackend.member.domain.Member;
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

  public String create(Member member) {
    LocalDateTime currentTime = LocalDateTime.now();
    return Jwts.builder()
        .setSubject(member.getEmail())
        .setIssuer("rebwon")
        .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
        .setExpiration(Date.from(currentTime
            .plusMinutes(expirationTime)
            .atZone(ZoneId.systemDefault()).toInstant()))
        .signWith(SignatureAlgorithm.HS512, signKey)
        .compact();
  }
}
