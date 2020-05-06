package com.capsulewardrobe.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Builder
public class ApiError {

  private String message;

  private HttpStatus status;

  @Builder.Default
  private String timestamp = new Date().toString();
}
