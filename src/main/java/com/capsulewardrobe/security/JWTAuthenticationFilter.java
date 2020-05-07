package com.capsulewardrobe.security;

import com.auth0.jwt.JWT;
import com.capsulewardrobe.models.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.capsulewardrobe.security.SecurityConstants.EXPIRATION_TIME;
import static com.capsulewardrobe.security.SecurityConstants.HEADER;
import static com.capsulewardrobe.security.SecurityConstants.JWT_SECRET;
import static com.capsulewardrobe.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
          HttpServletRequest req,
          HttpServletResponse res) throws AuthenticationException {

    try {
      ApplicationUser creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);

      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      creds.getEmail(),
                      creds.getPassword(),
                      new ArrayList<>()
              ));

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) {

    UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

    String token = JWT.create()
            .withSubject(userPrincipal.getId().toString())
            .withClaim("role", userPrincipal.getRole().getName())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(HMAC512(JWT_SECRET.getBytes()));
    res.addHeader(HEADER, TOKEN_PREFIX + token);
  }

}
