package id.ac.ui.cs.advprog.eshop.service;

import java.util.Map;

public class BankValidator implements PaymentValidator {
    @Override
    public boolean validate(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        return bankName != null && !bankName.isEmpty() &&
                referenceCode != null && !referenceCode.isEmpty();
    }
}