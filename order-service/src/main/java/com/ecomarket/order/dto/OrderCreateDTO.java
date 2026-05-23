package com.ecomarket.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String customerName;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar una dirección de correo válida")
    @Size(max = 100, message = "El correo electrónico no puede superar los 100 caracteres")
    private String customerEmail;

    @NotBlank(message = "La dirección de envío es obligatoria")
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String shippingAddress;

    @NotEmpty(message = "La orden debe contener al menos un producto")
    @Valid
    private List<OrderItemCreateDTO> items;
}
