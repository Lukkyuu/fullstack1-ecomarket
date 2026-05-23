package com.ecomarket.order.client;

import com.ecomarket.order.dto.ProductDTO;
import com.ecomarket.order.dto.StockReductionDTO;
import com.ecomarket.order.exception.InventoryServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient inventoryWebClient;

    public ProductDTO getProductById(Long productId) {
        log.info("Llamando a inventory-service para obtener producto con ID: {}", productId);
        try {
            return inventoryWebClient.get()
                    .uri("/api/products/{id}", productId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        log.error("Error cliente (4xx) al obtener producto ID {}: {}", productId, response.statusCode());
                        return response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new InventoryServiceException("Producto no válido o inexistente en inventario. Detalle: " + body)));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response -> {
                        log.error("Error servidor (5xx) al obtener producto ID {}: {}", productId, response.statusCode());
                        return Mono.error(new InventoryServiceException("Error del servidor de inventario al procesar el producto."));
                    })
                    .bodyToMono(ProductDTO.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (InventoryServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error de comunicación con el servicio de inventario para producto ID: {}", productId, ex);
            throw new InventoryServiceException("No se pudo comunicar con el servicio de inventario: " + ex.getMessage());
        }
    }

    public void reduceStock(List<StockReductionDTO> reductions) {
        log.info("Llamando a inventory-service para reducir stock de {} productos", reductions.size());
        try {
            inventoryWebClient.put()
                    .uri("/api/products/reduce-stock")
                    .bodyValue(reductions)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        log.error("Error cliente (4xx) al reducir stock: {}", response.statusCode());
                        return response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new InventoryServiceException("Error de validación o stock en inventario al reducir stock: " + body)));
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response -> {
                        log.error("Error servidor (5xx) al reducir stock: {}", response.statusCode());
                        return Mono.error(new InventoryServiceException("Error del servidor de inventario al actualizar el stock."));
                    })
                    .toBodilessEntity()
                    .timeout(Duration.ofSeconds(5))
                    .block();
            log.info("Reducción de stock realizada con éxito en el servicio de inventario");
        } catch (InventoryServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error de comunicación con el servicio de inventario al intentar reducir stock", ex);
            throw new InventoryServiceException("No se pudo comunicar con el servicio de inventario para actualizar el stock: " + ex.getMessage());
        }
    }
}
