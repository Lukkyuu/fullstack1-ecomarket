package com.ecomarket.inventoryservice.service;

import com.ecomarket.inventoryservice.dto.ProductDTO;
import com.ecomarket.inventoryservice.dto.StockReductionDTO;
import com.ecomarket.inventoryservice.entity.Product;
import com.ecomarket.inventoryservice.exception.ResourceNotFoundException;
import com.ecomarket.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el producto con ID: " + id));
    }

    @Transactional
    public void reduceStock(List<StockReductionDTO> reductions) {
        log.info("Reduciendo stock de {} productos", reductions.size());
        for (StockReductionDTO reduction : reductions) {
            Product p = productRepository.findById(reduction.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("No existe el producto con ID: " + reduction.getProductId()));
            if (p.getStock() < reduction.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + p.getName());
            }
            p.setStock(p.getStock() - reduction.getQuantity());
            productRepository.save(p);
        }
    }

    private ProductDTO toDTO(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .categoryId(p.getCategory().getId())
                .categoryName(p.getCategory().getName())
                .build();
    }
}
