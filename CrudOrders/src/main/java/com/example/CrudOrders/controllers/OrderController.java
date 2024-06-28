package com.example.CrudOrders.controllers;

import com.example.CrudOrders.model.Order;
import com.example.CrudOrders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    //Controller para obtener ordenes
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrders() {return orderService.getOrders();}

    //controller para crear una orden
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object>newOrder (@Valid @RequestBody Order order, BindingResult bindingResult) {
        //if para manejo de errores
        if (bindingResult.hasErrors()){
            List <String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return orderService.newOrder(order);
    }

    //Controller para actualizar productos
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateOrder(@PathVariable("id") Long id, @RequestBody Order updatedOrder) {
        return orderService.updateOrder(id, updatedOrder);
    }

    //Controller para eliminar ordenes
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }
}
