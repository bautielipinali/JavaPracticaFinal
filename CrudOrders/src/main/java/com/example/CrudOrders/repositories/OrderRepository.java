package com.example.CrudOrders.repositories;

import com.example.CrudOrders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{}
