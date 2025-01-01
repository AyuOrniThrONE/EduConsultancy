package com.pol.payment_service.mapper;

import com.pol.payment_service.entity.Payment;
import com.pol.payment_service.entity.PaymentStatus;
import com.razorpay.Order;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentMapper {
    public static Payment toEntity(Order order){
        Payment payment = new Payment();
        payment.setId(order.get("id") != null ? order.get("id").toString() : null);

        Object amount = order.get("amount");
        payment.setAmount(amount != null ? new BigDecimal(amount.toString()) : BigDecimal.ZERO);

        Object amountPaid = order.get("amount_paid");
        payment.setAmountPaid(amountPaid != null ? new BigDecimal(amountPaid.toString()) : BigDecimal.ZERO);

        Object amountDue = order.get("amount_due");
        payment.setAmountDue(amountDue != null ? new BigDecimal(amountDue.toString()) : BigDecimal.ZERO);

        payment.setCurrency(order.get("currency") != null ? order.get("currency").toString() : null);

        Object status = order.get("status");
        if (status != null && PaymentStatus.isValid(status.toString())) {
            payment.setStatus(PaymentStatus.valueOf(status.toString().toUpperCase()));
        } else {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        return payment;
    }

}
