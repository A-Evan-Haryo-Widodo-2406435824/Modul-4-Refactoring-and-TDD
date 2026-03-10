package id.ac.ui.cs.advprog.eshop.service;

import java.util.Map;

public interface PaymentValidator {
    public boolean validate(Map<String, String> paymentData);
}
