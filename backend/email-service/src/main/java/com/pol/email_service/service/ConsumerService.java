package com.pol.email_service.service;


//import com.pol.user_service.schema.avro.ForgotPasswordEvent;
//import com.pol.user_service.schema.avro.UserRegisteredEvent;
//import com.pol.payment_service.schema.avro.PaymentSuccessfulEvent;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Service;

//@Service
public class ConsumerService {

//    private final ResendService resendService;
//
//    public ConsumerService(ResendService resendService) {
//        this.resendService = resendService;
//    }

//    @KafkaListener(topics = "user-registered-topic", groupId = "notification-service")
//    public void processRegistrationOtp(UserRegisteredEvent event) {
//        try {
//            resendService.sendWelcomeEmail(event.getEmail().toString(),event.getName().toString());
//        } catch (Exception ex) {
//            System.err.println("Error processing event: " + ex.getMessage());
//        }
//    }
//
//    @KafkaListener(topics = "forgot-password-topic", groupId = "notification-service")
//    public void processForgotPasswordOtp(ForgotPasswordEvent event) {
//        try {
//            resendService.sendForgotPasswordOTPEmail(event.getEmail().toString(),event.getName().toString(),event.getOtp().toString());
//        } catch(Exception ex) {
//            System.err.println("Error processing event: " + ex.getMessage());
//        }
//    }
//
//    @KafkaListener(topics = "payment-successful-topic", groupId = "notification-service")
//    public void processSuccessfulPayment(PaymentSuccessfulEvent event) {
//        try {
//            System.out.println("recived payment succesfull event");
//            resendService.sendPaymentSuccesfulEmail(event.getEmail().toString(),event.getUsername().toString(),event.getSub().toString(),event.getPaymentId().toString(), event.getProductId().toString(), event.getOrderId().toString());
//        } catch(Exception ex) {
//            System.err.println("Error processing event: " + ex.getMessage());
//        }
//    }
}
