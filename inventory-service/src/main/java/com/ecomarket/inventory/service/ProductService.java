package com.ecomarket.inventory.service;

import com.ecomarket.inventory.dto.ProductCreateDTO;
import com.ecomarket.inventory.dto.ProductDTO;
import com.ecomarket.inventory.dto.StockReductionDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    List<ProductDTO> getProductsByCategory(Long categoryId);
    ProductDTO createProduct(ProductCreateDTO productCreateDTO);
    ProductDTO updateProduct(Long id, ProductCreateDTO productCreateDTO);
    void deleteProduct(Long id);
    void reduceStock(List<StockReductionDTO> reductions);
}
