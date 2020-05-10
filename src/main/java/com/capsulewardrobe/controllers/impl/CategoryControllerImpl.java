package com.capsulewardrobe.controllers.impl;

import com.capsulewardrobe.controllers.CategoryController;
import com.capsulewardrobe.models.Category;
import com.capsulewardrobe.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryControllerImpl implements CategoryController {

  private final CategoryService categoryService;

  public CategoryControllerImpl(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  public ResponseEntity<List<Category>> getAll() {

    List<Category> categories = categoryService.getAll();

    return ResponseEntity.ok(categories);
  }
}
