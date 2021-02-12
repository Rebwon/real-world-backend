package com.rebwon.realworldbackend.common.security;

import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberNotFoundException;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private final String signKey;
  private final MemberRepository memberRepository;

  public JwtAuthenticationFilter(@Value("${token.signKey}") String signKey, MemberRepository memberRepository) {
    this.signKey = signKey;
    this.memberRepository = memberRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    TokenExtractor tokenExtractor = new TokenExtractor(request.getHeader(AUTHORIZATION_HEADER));
    tokenExtractor.extract().ifPresent(rawAccessToken -> {

      TokenVerifier tokenVerifier = new TokenVerifier(signKey, rawAccessToken);
      String subject = tokenVerifier.parseClaims().orElseThrow(TokenParseErrorException::new);
      Member member = memberRepository.findByEmail(subject).orElseThrow(MemberNotFoundException::new);

      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(member, null, Collections.emptyList());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
      }
    });

    filterChain.doFilter(request, response);
  }
}