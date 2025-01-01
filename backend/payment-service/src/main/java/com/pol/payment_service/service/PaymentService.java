package com.pol.payment_service.service;

import com.pol.payment_service.client.EmailFeignClient;
import com.pol.payment_service.client.ProductFeignClient;
import com.pol.payment_service.dto.CoursePriceDTO;
import com.pol.payment_service.dto.PaymentRequestDTO;
import com.pol.payment_service.dto.PaymentSuccessRequestDto;
import com.pol.payment_service.entity.Payment;
import com.pol.payment_service.mapper.PaymentMapper;
import com.pol.payment_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//import com.pol.payment_service.schema.avro.PaymentSuccessfulEvent;
//import com.pol.payment_service.constants.KafkaTopics;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RazorpayClient razorpay;
    private final PaymentRepository paymentRepository;
    private final ProductFeignClient productFeignClient;
    private final EmailFeignClient emailFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
//    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Value("${razorpay.key_secret}")
    private String keySecret;


    public com.pol.payment_service.entity.Payment createOrder(PaymentRequestDTO paymentRequestDTO) throws RazorpayException {
        if(paymentRequestDTO.getProductId()==null){
            throw new RuntimeException("Please provide product id");
        }
        UUID productId = paymentRequestDTO.getProductId();
        CoursePriceDTO coursePriceDTO = productFeignClient.getCoursePriceById(productId);
        if(coursePriceDTO==null){
            throw new RuntimeException("Product not found with id : "+productId+" in payment service.");
        }
        JSONObject options = new JSONObject();
        options.put("amount", coursePriceDTO.getPrice().multiply(new BigDecimal(100)));
        options.put("currency", "INR");
        options.put("receipt", UUID.randomUUID().toString());

        Order order = razorpay.orders.create(options);
        Payment payment = PaymentMapper.toEntity(order);
        payment.setProductId(paymentRequestDTO.getProductId());
        payment.setUserId(UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        return payment;
    }

    public String updateOrder(Map<String,String> paymentDetails) throws RazorpayException {
        String razorpay_payment_id = paymentDetails.get("razorpay_payment_id");
        String razorpay_order_id = paymentDetails.get("razorpay_order_id");
        String razorpay_signature = paymentDetails.get("razorpay_signature");

//        PaymentSuccessfulEvent succesEvent = new PaymentSuccessfulEvent();
//        succesEvent.setEmail(paymentDetails.get("email"));
//        succesEvent.setSub(paymentDetails.get("sub"));
//        succesEvent.setPaymentId(paymentDetails.get("razorpay_payment_id"));
//        succesEvent.setUsername(paymentDetails.get("username"));
//        succesEvent.setProductId(paymentDetails.get("productId"));
//        succesEvent.setOrderId(paymentDetails.get("orderId"));

        PaymentSuccessRequestDto requestDTO = new PaymentSuccessRequestDto();
        requestDTO.setName(paymentDetails.get("username"));
        requestDTO.setEmail(paymentDetails.get("email"));
        requestDTO.setPaymentId(paymentDetails.get("razorpay_payment_id"));
        requestDTO.setProductId(paymentDetails.get("productId"));
        requestDTO.setOrderId(paymentDetails.get("orderId"));


        if(!paymentRepository.existsById(razorpay_order_id)){
            return "ORDER DOESN'T EXIST";
        }
        JSONObject options = new JSONObject(paymentDetails);
        boolean verified = Utils.verifyPaymentSignature(options,keySecret);
        if(verified){
            try {
//                kafkaTemplate.send(KafkaTopics.PaymentSuccessfulTopic, succesEvent);
//                logger.info("published Payment success event");
                emailFeignClient.sendPaymentSuccessEmail(requestDTO);
            } catch (Exception kafkaException) {
                logger.error("Failed to publish Payment Successful event to Kafka", kafkaException);
            }
            return "Successful";
        }
        return "Failed payment";
    }
}
