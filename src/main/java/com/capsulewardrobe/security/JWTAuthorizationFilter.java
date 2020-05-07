package com.capsulewardrobe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.capsulewardrobe.security.SecurityConstants.HEADER;
import static com.capsulewardrobe.security.SecurityConstants.JWT_SECRET;
import static com.capsulewardrobe.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private final Logger logger;

  public JWTAuthorizationFilter(
          AuthenticationManager authManager
  ) {
    super(authManager);
    this.logger = LoggerFactory.getLogger(getClass());
  }

  protected void doFilterInternal(
          HttpServletRequest req,
          HttpServletResponse res,
          FilterChain chain
  ) throws IOException, ServletException {

    String header = req.getHeader(HEADER);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    String token = header.replace(TOKEN_PREFIX, "");

    DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET.getBytes()))
            .build()
            .verify(token);

    String userId = jwt.getSubject();

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    authorities.add(new SimpleGrantedAuthority(jwt.getClaim("role").asString()));

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    chain.doFilter(req, res);
  }
}
