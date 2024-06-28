package com.example.CrudProducts.repositories;

import com.example.CrudProducts.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{}
