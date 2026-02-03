package com.ecommerce.products_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vendors")
public class VendorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El nombre del proveedor es obligatorio")
  @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
  @NotBlank(message = "El email del proveedor es obligatorio")
  @Email(message = "El email debe tener un formato válido")
  @Column(length = 255, nullable = false)
  private String email;

  @Column
  private String direccion;
}
