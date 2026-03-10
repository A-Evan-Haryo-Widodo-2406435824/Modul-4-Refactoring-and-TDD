package id.ac.ui.cs.advprog.eshop.service;

import java.util.Map;

public class VoucherValidator implements PaymentValidator {
    @Override
    public boolean validate(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
            int numericCount = 0;
            for (char c : voucherCode.toCharArray()) {
                if (Character.isDigit(c)) {
                    numericCount++;
                }
            }
            return numericCount == 8;
        }
        return false;
    }
}