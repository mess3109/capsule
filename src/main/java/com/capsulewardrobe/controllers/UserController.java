package com.capsulewardrobe.controllers;

import com.capsulewardrobe.api.request.UserRequest;
import com.capsulewardrobe.models.ApplicationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public interface UserController {

  @PostMapping
  ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest);

  @GetMapping("/{userId}")
  ResponseEntity<ApplicationUser> getUser(@PathVariable String userId);
}
