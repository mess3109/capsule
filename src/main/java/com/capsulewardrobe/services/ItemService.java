package com.capsulewardrobe.services;

import com.capsulewardrobe.exceptions.BadRequestException;
import com.capsulewardrobe.models.Item;
import com.capsulewardrobe.models.ApplicationUser;
import com.capsulewardrobe.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.capsulewardrobe.services.util.UuidUtility.generateUuid;

@Service
public class ItemService {

  private final ItemRepository itemRepository;
  private final UserService userService;

  public ItemService(ItemRepository itemRepository, UserService userService) {
    this.itemRepository = itemRepository;
    this.userService = userService;
  }

  public Item create(String userId, Item item) {

    ApplicationUser user = userService.get(userId);
    item.setUuid(generateUuid());
    item.setUser(user);

    return itemRepository.save(item);
  }

  public Item get(String userId, String itemId) throws BadRequestException {

    Item item = itemRepository.findByUuid(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));

    verifyUser(userId, item);

    return item;
  }

  private void verifyUser(String userId, Item item) throws BadRequestException {
    if (!item.getUser().getUuid().equals(userId)) {
      throw new BadRequestException("Item " + item + " does not belong to User " + userId);
    }
  }
}
