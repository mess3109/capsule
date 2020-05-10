package com.capsulewardrobe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Entity
@Getter
public class Category {

  private static final String SEQUENCE_NAME = "category_id_sequence";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(
          name = SEQUENCE_NAME,
          sequenceName = SEQUENCE_NAME,
          initialValue = 1,
          allocationSize = 1
  )
  @JsonIgnore
  private long id;

  private String title;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "parent_id")
  @JsonIgnore
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private Set<Category> subcategories;
}
