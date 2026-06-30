package com.ecomarket.reviewservice.controller;
import com.ecomarket.reviewservice.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewRepository repository;

    @InjectMocks
    private ReviewController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }
}


