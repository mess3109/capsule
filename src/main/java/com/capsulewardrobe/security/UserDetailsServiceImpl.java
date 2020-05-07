package com.capsulewardrobe.security;

import com.capsulewardrobe.models.ApplicationUser;
import com.capsulewardrobe.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    ApplicationUser user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

    return new UserPrincipal(user);
  }
}

