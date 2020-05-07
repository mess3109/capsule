package com.capsulewardrobe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Table(indexes = {@Index(columnList = "uuid")})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Item {

  private static final String SEQUENCE_NAME = "item_id_sequence";

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

  @Column(nullable = false, unique = true)
  @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
  private String uuid;

  private String title;

  private String color;

  private String brand;

  @ManyToOne
  @JoinColumn(name = "application_user_id")
  @JsonIgnore
  private ApplicationUser user;

  @ManyToMany(mappedBy = "items")
  @JsonIgnore
  private Set<Outfit> outfits = new HashSet<>();

  @Override
  public String toString() {
    return this.uuid;
  }
}
