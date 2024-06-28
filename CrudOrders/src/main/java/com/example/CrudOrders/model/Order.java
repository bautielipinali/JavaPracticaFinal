package com.example.CrudOrders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Este campo no puede estar vacio")
    private Long productId;

    @NotNull(message = "Este campo no puede estar vacio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private double unitPrice;

    @NotNull(message = "Este campo no puede ser nulo")
    @Min(value = 1, message = "Debe ser mayor a cero")
    private Long quantity;

    @NotNull(message = "Este campo no puede estar vacio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private double total;

    @NotNull(message = "Este campo no puede estar vacio")
    private LocalDate date;

    @NotBlank(message = "Este campo no puede estar vacio")
    private String notes;
}
