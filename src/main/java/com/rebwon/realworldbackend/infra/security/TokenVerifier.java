package com.rebwon.realworldbackend.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;

@Slf4j
public class TokenVerifier {

  private final String signKey;
  private final String token;

  public TokenVerifier(String signKey, String token) {
    this.signKey = signKey;
    this.token = token;
  }

  public Optional<String> parseClaims() {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(signKey).parseClaimsJws(token);
      return Optional.of(claims.getBody().getSubject());
    } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
      log.error("Invalid JWT Token", ex);
      throw new BadCredentialsException("Invalid JWT token: ", ex);
    } catch (ExpiredJwtException expiredEx) {
      log.info("JWT Token is expired", expiredEx);
      throw new TokenExpiredException();
    }
  }
}
