package com.example.CrudProducts.service;


import com.example.CrudProducts.model.Product;
import com.example.CrudProducts.repositories.ProductRepository;
import com.example.CrudProducts.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {


    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp (){
        MockitoAnnotations.openMocks(this);
    }


    //test obtener producto
    @Test
    public  void testGetProducts() {
        Product product = new Product(12L, "SKU001", "Description1", 100.0, true);
        List<Product> products = Collections.singletonList(product);
        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getProducts();
        assertEquals(1, result.size());
        assertEquals("SKU001", result.get(0).getSku());
    }


    //test eliminar producto
    @Test
    public void testDeleteProduct(){
        Long productId = 12L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ResponseEntity<Object> response = productService.deleteProduct(productId);

        //verificar el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());

        //verifica que el metodo delete se llamo correctamente
        verify(productRepository, times(1)).deleteById(productId);
    }


    //Test Add product
    @Test
    public void testNewProduct() {
        //producto ejemplo
        Product product = new Product();
        product.setSku("a06h89");
        product.setName("Producto de Prueba");
        product.setPrice(10.0);
        product.setStatus(true); // Cambiar a tipo Boolean

        ResponseEntity<Object> response = productService.newProduct(product);

        // Verificar el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product created successfully", response.getBody());

        // Verificar si el metodo save se llamó
        verify(productRepository, times(1)).save(product);
    }


   //Test producto actualizado
    @Test
    public void testUpdateProduct() {
        //producto ejemplo actual
        Long id = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setSku("a06h89");
        existingProduct.setName("Producto Existente");
        existingProduct.setPrice(10.0);
        existingProduct.setStatus(true); // Cambiar a tipo Boolean

        //producto ejemplo actualizado
        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setSku("b78j90");
        updatedProduct.setName("Producto actualizado");
        updatedProduct.setPrice(20.0);
        updatedProduct.setStatus(false); // Cambiar a tipo Boolean

        //simular el repositorio
        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        //invocar al metodo
        ResponseEntity<Object> response = productService.updateProduct(id, updatedProduct);

        //verificar el estado de resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated successfully", response.getBody());
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals("b78j90", existingProduct.getSku());
        assertEquals("Producto actualizado", existingProduct.getName());
        assertEquals(20.0, existingProduct.getPrice());
        assertEquals(false, existingProduct.getStatus());
    }

}
