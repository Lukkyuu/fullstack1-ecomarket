package com.ecomarket.couponservice.service;

import com.ecomarket.couponservice.entity.Coupon;
import com.ecomarket.couponservice.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository repository;

    @InjectMocks
    private CouponService service;

    @Test
    void givenValidActiveCoupon_whenValidateCoupon_thenReturnDiscount() {
        // Given
        String code = "DISCOUNT10";
        Coupon coupon = new Coupon();
        coupon.setCode(code);
        coupon.setActive(true);
        coupon.setDiscountPercent(10.0);
        when(repository.findByCode(code)).thenReturn(Optional.of(coupon));

        // When
        Double result = service.validateCoupon(code);

        // Then
        assertEquals(10.0, result);
        verify(repository, times(1)).findByCode(code);
    }

    @Test
    void givenInactiveCoupon_whenValidateCoupon_thenReturnZero() {
        // Given
        String code = "DISCOUNT10";
        Coupon coupon = new Coupon();
        coupon.setCode(code);
        coupon.setActive(false);
        coupon.setDiscountPercent(10.0);
        when(repository.findByCode(code)).thenReturn(Optional.of(coupon));

        // When
        Double result = service.validateCoupon(code);

        // Then
        assertEquals(0.0, result);
        verify(repository, times(1)).findByCode(code);
    }

    @Test
    void givenInvalidCoupon_whenValidateCoupon_thenReturnZero() {
        // Given
        String code = "INVALID";
        when(repository.findByCode(code)).thenReturn(Optional.empty());

        // When
        Double result = service.validateCoupon(code);

        // Then
        assertEquals(0.0, result);
        verify(repository, times(1)).findByCode(code);
    }
}
