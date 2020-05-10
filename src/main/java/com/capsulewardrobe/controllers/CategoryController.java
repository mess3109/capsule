package com.capsulewardrobe.controllers;

import com.capsulewardrobe.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public interface CategoryController {

  @GetMapping
  ResponseEntity<List<Category>> getAll();
}
