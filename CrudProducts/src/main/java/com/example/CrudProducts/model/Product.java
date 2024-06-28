package com.example.CrudProducts.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Este campo no puede estar vacio")
    private String sku;

    @NotBlank(message = "Este campo no puede estar vacio")
    private String name;

    @NotNull(message = "Este campo no puede ser nulo")
    @DecimalMin(value = "0.0", message = "Debe ser mayor o igual a cero")
    private double price;

    private Boolean status;
}
