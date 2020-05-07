package com.capsulewardrobe.security;

public class SecurityConstants {

  //TODO move secret out of repo
  static final String JWT_SECRET = "secret";

  static final String HEADER = "Authorization";
  static final String TOKEN_PREFIX = "Bearer ";
  static final long EXPIRATION_TIME = 86_400_000; // 1 days in milliseconds
}
