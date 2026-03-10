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
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", "SUCCESS", paymentData, order);

        assertEquals(paymentId, payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData.get("voucherCode"), payment.getPaymentData().get("voucherCode"));

    }

    @Test
    void testCreatePaymentEmptyOrder() {
        this.order = null;
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("28ef3989-58d5-4a5c-9a23-c1b84ee26dc6",
                    "VOUCHER", "SUCCESS", paymentData, order);
        });
    }





    @Test
    void testCreatePaymentWithBankTransferSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", "SUCCESS", paymentData, order);

        assertEquals(paymentId, payment.getId());
        assertEquals("BANK", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData.get("bankName"), payment.getPaymentData().get("bankName"));
        assertEquals(paymentData.get("referenceCode"), payment.getPaymentData().get("referenceCode"));

    }


    @Test
    void testSetStatusToSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", "REJECTED", paymentData, order);

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());

    }

    @Test
    void testSetStatusToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", "SUCCESS", paymentData, order);

        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());

    }

    @Test
    void testSetStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", "SUCCESS", paymentData, order);

        assertThrows(IllegalArgumentException.class,
                () -> payment.setStatus("HOHO")
        );


    }



}
