package com.capsulewardrobe.controllers;

import com.capsulewardrobe.api.request.ItemsRequest;
import com.capsulewardrobe.exceptions.BadRequestException;
import com.capsulewardrobe.models.Outfit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RequestMapping(value = "/users/{userId}/outfits")
public interface OutfitController {

  @PostMapping
  ResponseEntity<Void> createOutfit(@PathVariable String userId, @RequestBody Outfit outfit);

  @GetMapping("/{outfitId}")
  ResponseEntity<Outfit> getOutfit(
          @PathVariable String userId,
          @PathVariable String outfitId
  ) throws BadRequestException;

  @PostMapping("/{outfitId}/items")
  ResponseEntity<Outfit> addItems(
          @PathVariable String userId,
          @PathVariable String outfitId,
          @RequestBody ItemsRequest itemsRequest
  ) throws BadRequestException;

  @DeleteMapping("/{outfitId}/items")
  ResponseEntity<Outfit> removeItems(
          @PathVariable String userId,
          @PathVariable String outfitId,
          @RequestBody ItemsRequest itemsRequest
  ) throws BadRequestException;
}
