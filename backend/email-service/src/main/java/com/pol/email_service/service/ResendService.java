package com.pol.email_service.service;


import com.pol.email_service.utils.EmailTemplates;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ResendService {

    @Value("${resend.from-email}")
    private String fromEmail;

    private final Resend resend;
    private final EmailTemplates emailTemplates = EmailTemplates.getInstance();

    public ResendService(Resend resend) {
        this.resend = resend;
    }


    public String sendWelcomeEmail(String to, String userName) {

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject("Welcome, "+userName)
                .html(emailTemplates.getWelcomeTemplate(userName))
                .build();

        CreateEmailResponse response = null;
        try {
            response = resend.emails().send(options);
            System.out.println("Mail sended Successfully");
        } catch (ResendException e) {
            throw new RuntimeException("There was a error while sending email. Please try again later.");
        }
        return response.getId();
    }


    public String sendForgotPasswordOTPEmail(String to, String name,String otp) {
        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject("OTP VERIFICATION")
                .html(emailTemplates.getForgotPasswordTemplate(name,otp))
                .build();

        CreateEmailResponse response = null;
        try {
            response = resend.emails().send(options);
        } catch (ResendException e) {
            throw new RuntimeException("There was a error while sending email. Please try again later.");
        }
        return response.getId();
    }

    public String sendPaymentSuccesfulEmail(String to, String name,String paymentId, String productId, String orderId) {
        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromEmail)
                .to(to)
                .subject("Payment Successful")
                .html(emailTemplates.getPaymentConfirmationTemplate(to,name,paymentId,productId, orderId))
                .build();

        CreateEmailResponse response = null;
        try {
            response = resend.emails().send(options);
        } catch (ResendException e) {
            throw new RuntimeException("There was a error while sending email. Please try again later.");
        }
        return response.getId();
    }
}
