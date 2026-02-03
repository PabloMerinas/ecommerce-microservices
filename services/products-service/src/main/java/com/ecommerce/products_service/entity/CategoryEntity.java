package com.ecommerce.products_service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El nombre de la categoría es obligatorio")
  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "parent_category_id")
  private CategoryEntity parentCategory;

  @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
  private Set<CategoryEntity> subCategories = new HashSet<>();
}
