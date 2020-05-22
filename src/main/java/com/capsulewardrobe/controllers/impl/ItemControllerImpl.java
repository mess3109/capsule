package com.capsulewardrobe.controllers.impl;

import com.capsulewardrobe.controllers.ItemController;
import com.capsulewardrobe.exceptions.BadRequestException;
import com.capsulewardrobe.models.Item;
import com.capsulewardrobe.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.net.URI;

@Controller
public class ItemControllerImpl implements ItemController {

  private final ItemService itemService;

  public ItemControllerImpl(ItemService itemService) {
    this.itemService = itemService;
  }

  public ResponseEntity<Void> createItem(String userId, Item item) {

    Item createdItem = itemService.create(userId, item);

    return ResponseEntity.created(URI.create("/items/" + createdItem.getUuid())).build();
  }

  public ResponseEntity<Item> getItem(String userId, String itemId) throws BadRequestException {

    return new ResponseEntity<>(itemService.get(userId, itemId), HttpStatus.OK);
  }
}
