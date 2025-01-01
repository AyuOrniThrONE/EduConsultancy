package com.pol.payment_service.entity;

public enum PaymentStatus {
    CREATED, PAID, CANCELLED;

    public static boolean isValid(String value) {
        try {
            PaymentStatus.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

