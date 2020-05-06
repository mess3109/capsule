package com.capsulewardrobe.repositories;

import com.capsulewardrobe.models.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Long> {

  Optional<Outfit> findByUuid(String uuid);
}
