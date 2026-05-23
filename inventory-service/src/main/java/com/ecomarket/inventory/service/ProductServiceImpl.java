package com.ecomarket.inventory.service;

import com.ecomarket.inventory.dto.ProductCreateDTO;
import com.ecomarket.inventory.dto.ProductDTO;
import com.ecomarket.inventory.dto.StockReductionDTO;
import com.ecomarket.inventory.entity.Category;
import com.ecomarket.inventory.entity.Product;
import com.ecomarket.inventory.exception.InsufficientStockException;
import com.ecomarket.inventory.exception.ResourceNotFoundException;
import com.ecomarket.inventory.repository.CategoryRepository;
import com.ecomarket.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        log.info("Obteniendo todos los productos");
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        log.info("Obteniendo producto con ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con id: " + id);
                });
        return convertToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        log.info("Obteniendo productos para la categoría con ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            log.error("Categoría no encontrada con ID: {}", categoryId);
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + categoryId);
        }
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateDTO dto) {
        log.info("Creando nuevo producto: {}", dto.getName());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Error al crear producto: Categoría no encontrada con ID: {}", dto.getCategoryId());
                    return new ResourceNotFoundException("Categoría no encontrada con id: " + dto.getCategoryId());
                });

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        log.info("Producto creado exitosamente con ID: {}", saved.getId());
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductCreateDTO dto) {
        log.info("Actualizando producto con ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con id: " + id);
                });

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Error al actualizar producto: Categoría no encontrada con ID: {}", dto.getCategoryId());
                    return new ResourceNotFoundException("Categoría no encontrada con id: " + dto.getCategoryId());
                });

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        log.info("Producto con ID: {} actualizado exitosamente", updated.getId());
        return convertToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al eliminar: Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Producto no encontrado con id: " + id);
                });
        productRepository.delete(product);
        log.info("Producto con ID: {} eliminado exitosamente", id);
    }

    @Override
    @Transactional
    public void reduceStock(List<StockReductionDTO> reductions) {
        log.info("Procesando reducción de stock para {} productos", reductions.size());
        for (StockReductionDTO reduction : reductions) {
            Product product = productRepository.findById(reduction.getProductId())
                    .orElseThrow(() -> {
                        log.error("Reducción de stock fallida: Producto no encontrado con ID: {}", reduction.getProductId());
                        return new ResourceNotFoundException("Producto no encontrado con id: " + reduction.getProductId());
                    });

            if (product.getStock() < reduction.getQuantity()) {
                log.error("Reducción de stock fallida para producto '{}' (ID: {}). Stock actual: {}, Cantidad solicitada: {}",
                        product.getName(), product.getId(), product.getStock(), reduction.getQuantity());
                throw new InsufficientStockException("Stock insuficiente para el producto '" + product.getName() +
                        "'. Stock disponible: " + product.getStock() + ", requerido: " + reduction.getQuantity());
            }

            int newStock = product.getStock() - reduction.getQuantity();
            product.setStock(newStock);
            productRepository.save(product);
            log.info("Stock actualizado para producto ID {}: {} -> {}", product.getId(), product.getStock() + reduction.getQuantity(), newStock);
        }
        log.info("Reducción de stock completada exitosamente.");
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
