package com.ecomarket.couponservice.service;

import com.ecomarket.couponservice.entity.Coupon;
import com.ecomarket.couponservice.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository repository;

    public Double validateCoupon(String code) {
        log.info("Validando cupón: {}", code);
        return repository.findByCode(code)
                .filter(Coupon::getActive)
                .map(Coupon::getDiscountPercent)
                .orElse(0.0);
    }
}
