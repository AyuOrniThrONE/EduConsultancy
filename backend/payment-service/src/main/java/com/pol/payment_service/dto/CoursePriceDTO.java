package com.pol.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CoursePriceDTO {
    private UUID id;
    private BigDecimal price;
}
