package com.capsulewardrobe.controllers.impl;

import com.capsulewardrobe.api.request.UserRequest;
import com.capsulewardrobe.controllers.UserController;
import com.capsulewardrobe.models.ApplicationUser;
import com.capsulewardrobe.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserControllerImpl implements UserController {

  private final UserService userService;

  public UserControllerImpl(UserService userService) {
    this.userService = userService;
  }

  public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest) {

    ApplicationUser user = userService.create(userRequest);

    System.out.println(user.getUuid());

    return ResponseEntity.created(URI.create("/users/" + user.getUuid())).build();
  }

  public ResponseEntity<ApplicationUser> getUser(String userId) {

    return new ResponseEntity<>(userService.get(userId), HttpStatus.OK);
  }
}
