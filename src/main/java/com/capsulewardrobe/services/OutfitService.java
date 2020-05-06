package com.capsulewardrobe.services;

import com.capsulewardrobe.api.request.ItemsRequest;
import com.capsulewardrobe.exceptions.BadRequestException;
import com.capsulewardrobe.models.Item;
import com.capsulewardrobe.models.Outfit;
import com.capsulewardrobe.models.ApplicationUser;
import com.capsulewardrobe.repositories.OutfitRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static com.capsulewardrobe.services.util.UuidUtility.generateUuid;

@Service
@Transactional
public class OutfitService {

  private final OutfitRepository outfitRepository;
  private final UserService userService;
  private final ItemService itemService;

  public OutfitService(OutfitRepository outfitRepository, UserService userService, ItemService itemService) {
    this.outfitRepository = outfitRepository;
    this.userService = userService;
    this.itemService = itemService;
  }

  public Outfit create(String userId, Outfit outfit) {

    ApplicationUser user = userService.get(userId);

    outfit.setUuid(generateUuid());
    outfit.setUser(user);

    return outfitRepository.save(outfit);
  }

  public Outfit get(String userId, String outfitId) throws BadRequestException {

    Outfit outfit = outfitRepository.findByUuid(outfitId).orElseThrow(() -> new EntityNotFoundException("Outfit " + outfitId + " not found"));

    verifyUser(userId, outfit);

    return outfit;
  }

  public Outfit addItems(String userId, String outfitId, ItemsRequest itemsRequest) throws BadRequestException {

    Outfit outfit = get(userId, outfitId);

    verifyUser(userId, outfit);

    Set<Item> items = new HashSet<>();

    for (String itemId : itemsRequest.getItemIds()) {
      items.add(itemService.get(userId, itemId));
    }

    outfit.getItems().addAll(items);

    return outfitRepository.save(outfit);
  }

  public Outfit removeItems(String userId, String outfitId, ItemsRequest itemsRequest) throws BadRequestException {

    Outfit outfit = get(userId, outfitId);

    verifyUser(userId, outfit);

    Set<Item> items = new HashSet<>();

    for (String itemId : itemsRequest.getItemIds()) {
      items.add(itemService.get(userId, itemId));
    }

    outfit.getItems().removeAll(items);

    return outfitRepository.save(outfit);
  }

  private void verifyUser(String userId, Outfit outfit) throws BadRequestException {
    if (!outfit.getUser().getUuid().equals(userId)) {
      throw new BadRequestException("Outfit " + outfit + " does not belong to User " + userId);
    }
  }
}
