package com.example.CrudOrders.services;

import com.example.CrudOrders.model.Order;
import com.example.CrudOrders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OrderRepository orderRepository;

    //obtener ordenes
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    //crear orden
    public ResponseEntity<Object> newOrder(Order order) {
        Long productId = order.getProductId();
        String url = "http://localhost:8083/api/products/find/" + productId;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            //if para verificar que el producto se encuentra disponible
            if (response.getStatusCode() == HttpStatus.OK) {
                orderRepository.save(order);
                return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Product not found, order not created", HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return new ResponseEntity<>("Product not found, order not created", HttpStatus.NOT_FOUND);
        }
    }

    //eliminar orden
    public ResponseEntity<Object> deleteOrder(Long id) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            orderRepository.deleteById(id);
            return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }

    //Actualizar orden
    public ResponseEntity<Object> updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setProductId(updatedOrder.getProductId());
            existingOrder.setUnitPrice(updatedOrder.getUnitPrice());
            existingOrder.setQuantity(updatedOrder.getQuantity());
            existingOrder.setTotal(updatedOrder.getTotal());
            existingOrder.setDate(updatedOrder.getDate());
            existingOrder.setNotes(updatedOrder.getNotes());

            orderRepository.save(existingOrder);

            return new ResponseEntity<>("Order updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
    }
}
