package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("37ef3989-58d5-4a5c-9a23-c1b84ee26dc6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("1521fdb0-96e4-4a01-ba9d-4566d6697287",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testSaveCreate() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData, order);

        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertSame(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment_1 = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData, order);
        paymentRepository.save(payment_1);

        Payment payment_2 = new Payment(paymentId, "VOUCHER", PaymentStatus.REJECTED.getValue(), paymentData, order);
        paymentRepository.save(payment_2);


        Payment findResult = paymentRepository.findById(payment_1.getId());
        assertEquals(payment_2.getId(), findResult.getId());
        assertEquals(payment_2.getStatus(), findResult.getStatus());
        assertEquals(payment_2.getMethod(), findResult.getMethod());
        assertSame(payment_2.getOrder(), findResult.getOrder());
        assertSame(payment_2.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData, order);
        paymentRepository.save(payment);


        Payment findResult = paymentRepository.findById(payment.getId());
        assertNotNull(findResult);

        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertSame(payment.getOrder(), findResult.getOrder());
        assertSame(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdNotFound() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData, order);
        paymentRepository.save(payment);


        Payment findResult = paymentRepository.findById("hehe");
        assertNull(findResult);
    }

    @Test
    void testFindAllSuccess() {
        Map<String, String> paymentData_1 = new HashMap<>();
        paymentData_1.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId_1 = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment_1 = new Payment(paymentId_1, "VOUCHER", PaymentStatus.REJECTED.getValue(), paymentData_1, order);
        paymentRepository.save(payment_1);

        Map<String, String> paymentData_2 = new HashMap<>();
        paymentData_2.put("voucherCode", "ESHOP1234ABC5679");
        String paymentId_2 = "458fa642-e16d-4888-92fa-3fc86f8d030c";
        Payment payment_2 = new Payment(paymentId_2, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData_2, order);
        paymentRepository.save(payment_2);

        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());

    }

    @Test
    void testFindAllEmpty() {
        List<Payment> allPayments = paymentRepository.findAll();
        assertTrue(allPayments.isEmpty());
    }



}
