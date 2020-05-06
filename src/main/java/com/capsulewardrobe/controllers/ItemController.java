package com.capsulewardrobe.controllers;

import com.capsulewardrobe.exceptions.BadRequestException;
import com.capsulewardrobe.models.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/users/{userId}/items")
public interface ItemController {

  @PostMapping
  ResponseEntity<Void> createItem(@PathVariable String userId, @RequestBody Item item);

  @GetMapping("/{itemId}")
  ResponseEntity<Item> getItem(@PathVariable String userId, @PathVariable String itemId) throws BadRequestException;
}
