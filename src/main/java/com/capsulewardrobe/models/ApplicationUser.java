package com.capsulewardrobe.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Table(indexes = {@Index(columnList = "uuid"), @Index(columnList = "email")})
@Entity
@EqualsAndHashCode(of = "uuid", callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ApplicationUser {

  private static final String SEQUENCE_NAME = "application_user_id_sequence";

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

  @Column(nullable = false, unique = true)
  private String email;

  @JsonIgnore
  private String password;

  private String name;

  private Role role;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Item> items = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Outfit> outfits = new HashSet<>();

  @Override
  public String toString() {
    return this.uuid;
  }
}
