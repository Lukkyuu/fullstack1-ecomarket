package com.ecomarket.inventory.service;

import com.ecomarket.inventory.dto.CategoryDTO;
import com.ecomarket.inventory.entity.Category;
import com.ecomarket.inventory.exception.ResourceNotFoundException;
import com.ecomarket.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        log.info("Obteniendo lista de todas las categorías");
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        log.info("Obteniendo categoría con ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada con id: " + id);
                });
        return convertToDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        log.info("Creando nueva categoría con nombre: {}", categoryDTO.getName());
        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            log.error("Error al crear categoría: ya existe una categoría con nombre: {}", categoryDTO.getName());
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + categoryDTO.getName());
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
        Category saved = categoryRepository.save(category);
        log.info("Categoría creada exitosamente con ID: {}", saved.getId());
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        log.info("Actualizando categoría con ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: Categoría no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada con id: " + id);
                });

        categoryRepository.findByName(categoryDTO.getName())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        log.error("Error al actualizar categoría: El nombre '{}' ya está en uso", categoryDTO.getName());
                        throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + categoryDTO.getName());
                    }
                });

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category updated = categoryRepository.save(category);
        log.info("Categoría con ID: {} actualizada exitosamente", updated.getId());
        return convertToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Eliminando categoría con ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al eliminar: Categoría no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Categoría no encontrada con id: " + id);
                });
        categoryRepository.delete(category);
        log.info("Categoría con ID: {} eliminada exitosamente", id);
    }

    private CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
