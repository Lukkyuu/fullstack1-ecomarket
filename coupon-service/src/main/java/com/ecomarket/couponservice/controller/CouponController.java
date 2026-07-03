package com.ecomarket.couponservice.controller;
import com.ecomarket.couponservice.entity.Coupon;
import com.ecomarket.couponservice.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@Tag(name = "Coupon", description = "Endpoints para la gestion de Coupon")
@RestController @RequestMapping("/api/coupons") @RequiredArgsConstructor @Slf4j
public class CouponController {
    private final CouponService service;
    
    @Operation(summary = "Obtener registro por ID u otro criterio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping("/validate/{code}")
    public ResponseEntity<Double> validateCoupon(@PathVariable String code) {
        return ResponseEntity.ok(service.validateCoupon(code));
    }
}
