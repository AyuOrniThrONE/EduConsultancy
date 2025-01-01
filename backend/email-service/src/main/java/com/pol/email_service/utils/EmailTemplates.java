package com.pol.email_service.utils;

public class EmailTemplates {
    private static final EmailTemplates INSTANCE = new EmailTemplates();

    private EmailTemplates(){}

    public static EmailTemplates getInstance(){
        return INSTANCE;
    }

    public String getWelcomeTemplate(String username) {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Welcome to Our Service</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
                    h2 { color: #4CAF50; }
                    p { line-height: 1.6; }
                    .button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #4CAF50; text-decoration: none; border-radius: 5px; }
                    .footer { margin-top: 20px; font-size: 12px; color: #888888; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Welcome, %s!</h2>
                    <p>We're excited to have you on board. Get started by clicking the button below to log in to your account:</p>
                    <p>If you have any questions or need help, feel free to reach out to our support team.</p>
                    <p>Thanks for joining us,<br>Your Company Team</p>
                    <div class="footer">
                        <p>&copy; 2024 Your Company. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(username);
    }

    public String getForgotPasswordTemplate(String username, String otp) {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset</title>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 5px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
                    h2 { color: #4CAF50; }
                    p { line-height: 1.6; }
                    .button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #4CAF50; text-decoration: none; border-radius: 5px; }
                    .footer { margin-top: 20px; font-size: 12px; color: #888888; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Password Reset Request</h2>
                    <p>Hi %s,</p>
                    <p>We received a request to reset your password. Your OTP is %s</p>
                    <p>If you didn't request a password reset, please ignore this email or contact support if you have any concerns.</p>
                    <p>Thanks,<br>Your Company Team</p>
                    <div class="footer">
                        <p>&copy; 2024 Your Company. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
        """.formatted(username,otp);
    }

    public String getPaymentConfirmationTemplate(String email, String username, String paymentId, String productId, String orderId) {
        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Payment Confirmation</title>
            <style>
                body { font-family: 'Arial', sans-serif; background-color: #f5f3ff; color: #4a356d; margin: 0; padding: 0; }
                .container { max-width: 600px; margin: 30px auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); }
                h2 { color: #6b21a8; margin-bottom: 10px; }
                p { line-height: 1.6; margin: 10px 0; }
                .info { background-color: #ede9fe; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
                .info p { margin: 5px 0; }
                .button { display: inline-block; padding: 10px 20px; margin-top: 20px; color: #ffffff; background-color: #6b21a8; text-decoration: none; border-radius: 5px; }
                .footer { margin-top: 20px; font-size: 12px; color: #6b7280; text-align: center; }
                a { color: #6b21a8; text-decoration: none; }
                a:hover { text-decoration: underline; }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Payment Confirmation</h2>
                <p>Hi %s,</p>
                <p>Thank you for your payment! Your transaction was successful. Below are the details:</p>
                <div class="info">
                    <p><strong>Payment ID:</strong> %s</p>
                    <p><strong>Order ID:</strong> %s</p>
                </div>
                <p>If you have any questions, feel free to reach out to us at <a href="mailto:%s">%s</a>.</p>
                <p>We appreciate your trust in our service!<br>Warm regards,<br>EduCon</p>
                <div class="footer">
                    <p>&copy; 2024 EduCon. All rights reserved.</p>
                </div>
            </div>
        </body>
        </html>
    """.formatted(username, paymentId, orderId, email, email);
    }



}