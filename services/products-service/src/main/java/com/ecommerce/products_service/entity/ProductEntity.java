package com.ecommerce.products_service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entidad que representa un producto en el catálogo.
 * Incluye validaciones de negocio y auditoría automática.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class ProductEntity {

  /**
   * Identificador único del producto.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Código de referencia único del producto (SKU).
   */
  @NotBlank(message = "La referencia del producto es obligatoria")
  @Size(
    min = 3,
    max = 50,
    message = "La referencia debe tener entre 3 y 50 caracteres"
  )
  @Column(nullable = false, unique = true, length = 50)
  private String reference;

  /**
   * Nombre del producto.
   */
  @NotBlank(message = "El nombre del producto es obligatorio")
  @Size(
    min = 2,
    max = 255,
    message = "El nombre debe tener entre 3 y 255 caracteres"
  )
  @Column(nullable = false, length = 255)
  private String name;

  /**
   * Descripción detallada del producto.
   */
  @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
  @Column(length = 2000)
  private String description;

  /**
   * Precio unitario del producto.
   */
  @NotNull(message = "El precio es obligatorio")
  @Positive(message = "El precio debe ser mayor a 0")
  @Digits(
    integer = 10,
    fraction = 2,
    message = "El precio debe tener como máximo 10 enteros y 2 decimales"
  )
  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  /**
   * Cantidad disponible en inventario.
   */
  @NotNull(message = "El stock es obligatorio")
  @PositiveOrZero(message = "El stock no puede ser negativo")
  @Column(nullable = false)
  private Integer stock;

  /**
   * Indica si el producto está disponible para la venta.
   */
  @NotNull(
    message = "Es obligatorio espeficicar si el producto está disponible"
  )
  @Column(nullable = false)
  private Boolean available;

  /**
   * Marca de tiempo de creación (auditoria).
   */
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  /**
   * Marca de tiempo de última actualización (auditoria).
   */
  @UpdateTimestamp
  @Column(nullable = false)
  private Instant updatedAt;

  /**
   * Versión para control de concurrencia optimista.
   */
  @Version
  @Column(nullable = false)
  private Long version;

  // Upcoming: Category relationship
  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(
    name = "product_categories",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  @Builder.Default
  private Set<CategoryEntity> categories = new HashSet<>();

  @ManyToOne(optional = false)
  @JoinColumn(name = "vendor_id", nullable = false)
  private VendorEntity vendor;
}
