package com.capsulewardrobe.repositories;

import com.capsulewardrobe.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

  Optional<ApplicationUser> findByUuid(String uuid);

  Optional<ApplicationUser> findByEmail(String uuid);
}
