package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("37ef3989-58d5-4a5c-9a23-c1b84ee26dc6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("1521fdb0-96e4-4a01-ba9d-4566d6697287",
                products, 1708560000L, "Safira Sudrajat");

    }

    @Test
    void testCreatePaymentWithVoucherCodeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = String.format("payment-voucher-%s", order.getId());

        Payment payment = new Payment(paymentId, "VOUCHER", paymentData, order);

        assertEquals(paymentId, payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData.get("voucherCode"), payment.getPaymentData().get("voucherCode"));

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }



    @Test
    void testCreatePaymentWithVoucherCodeInvalidLengthCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567");
        String paymentId = String.format("payment-voucher-%s", order.getId());

        Payment payment = new Payment(paymentId, "VOUCHER", paymentData, order);

        assertNotEquals(16, payment.getPaymentData().get("voucherCode").length());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherCodeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", null);
        String paymentId = String.format("payment-voucher-%s", order.getId());

        Payment payment = new Payment(paymentId, "VOUCHER", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherCodeEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "");
        String paymentId = String.format("payment-voucher-%s", order.getId());

        Payment payment = new Payment(paymentId, "VOUCHER", paymentData, order);

        assertNotEquals(16, payment.getPaymentData().get("voucherCode").length());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherCodeInvalidStartedChars() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SSHOP1234ABC567");
        String paymentId = String.format("payment-voucher-%s", order.getId());

        Payment payment = new Payment(paymentId, "VOUCHER", paymentData, order);

        assertNotEquals("ESHOP", payment.getId().substring(0, 5));
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithVoucherCodeDoesNotContainEightNumericalChars() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPA234ABC567");
        String paymentId = String.format("payment-voucher-%s", order.getId());

        Payment payment = new Payment(paymentId, "VOUCHER", paymentData, order);

        assertNotEquals(8, payment.getId().replaceAll("\\D", "").length());
        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }


    @Test
    void testCreatePaymentWithBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals(paymentId, payment.getId());
        assertEquals("BANK", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData.get("bankName"), payment.getPaymentData().get("bankName"));
        assertEquals(paymentData.get("referenceCode"), payment.getPaymentData().get("referenceCode"));

        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferIfPaymentDataEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferIfPaymentDataNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", null);
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferIfPaymentDataBankNameEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF12345");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferIfPaymentDataBankNameNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF12345");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferIfPaymentDataReferenceCodeEmptyString() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferIfPaymentDataReferenceCodeNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusToSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());

    }

    @Test
    void testSetStatusToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());

    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = String.format("payment-bank-%s", order.getId());

        Payment payment = new Payment(paymentId, "BANK", paymentData, order);

        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("HOHO")
        );


    }



}
