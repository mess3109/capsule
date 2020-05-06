package com.capsulewardrobe.services;

import com.capsulewardrobe.api.request.UserRequest;
import com.capsulewardrobe.models.ApplicationUser;
import com.capsulewardrobe.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static com.capsulewardrobe.services.util.UuidUtility.generateUuid;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ApplicationUser create(UserRequest userRequest) {

    ApplicationUser user = new ApplicationUser();
    user.setUuid(generateUuid());
    user.setEmail(userRequest.getEmail());
    user.setName(userRequest.getName());

    return userRepository.save(user);
  }

  public ApplicationUser get(String userId) {

    return userRepository.findByUuid(userId).orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
  }
}
