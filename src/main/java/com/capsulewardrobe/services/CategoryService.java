package com.capsulewardrobe.services;

import com.capsulewardrobe.models.Category;
import com.capsulewardrobe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getAll() {

    return categoryRepository.findByParent(null);
  }
}
