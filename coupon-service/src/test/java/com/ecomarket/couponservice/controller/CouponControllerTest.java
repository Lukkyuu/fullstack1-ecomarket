package com.ecomarket.couponservice.controller;

import com.ecomarket.couponservice.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @Mock
    private CouponService service;

    @InjectMocks
    private CouponController controller;

    @Test
    void givenValidCode_whenValidateCoupon_thenReturnDiscount() {
        // Given
        String code = "DISCOUNT10";
        when(service.validateCoupon(code)).thenReturn(10.0);

        // When
        ResponseEntity<Double> response = controller.validateCoupon(code);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(10.0, response.getBody());
        verify(service, times(1)).validateCoupon(code);
    }
}
