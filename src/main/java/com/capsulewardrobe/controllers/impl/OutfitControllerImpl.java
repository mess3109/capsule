package com.capsulewardrobe.controllers.impl;

import com.capsulewardrobe.api.request.ItemsRequest;
import com.capsulewardrobe.controllers.OutfitController;
import com.capsulewardrobe.exceptions.BadRequestException;
import com.capsulewardrobe.models.Outfit;
import com.capsulewardrobe.services.OutfitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class OutfitControllerImpl implements OutfitController {

  private final OutfitService outfitService;

  public OutfitControllerImpl(OutfitService outfitService) {
    this.outfitService = outfitService;
  }

  @Override
  public ResponseEntity<Void> createOutfit(String userId, Outfit outfit) {

    outfitService.create(userId, outfit);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<Outfit> getOutfit(String userId, String outfitId) throws BadRequestException {

    return new ResponseEntity<>(outfitService.get(userId, outfitId), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Outfit> addItems(
          String userId,
          String outfitId,
          ItemsRequest itemsRequest
  ) throws BadRequestException {

    return new ResponseEntity<>(outfitService.addItems(userId, outfitId, itemsRequest), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Outfit> removeItems(
          String userId,
          String outfitId,
          ItemsRequest itemsRequest
  ) throws BadRequestException {

    return new ResponseEntity<>(outfitService.removeItems(userId, outfitId, itemsRequest), HttpStatus.OK);
  }
}
