package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("8521fdb0-96e4-4a01-ba9d-4566d6697287");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("1521fdb0-96e4-4a01-ba9d-4566d6697287",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPaymentVoucherValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());

    }

    @Test
    void testAddPaymentVoucherInvalidLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SSHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidNumericLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC567S");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-1234567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.SUCCESS.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidEmptyBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF-1234567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF-1234567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidEmptyReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", null);
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidNullBankAndReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", null);
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalidEmptyBankAndReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }


    @Test
    void testUpdateStatusSuccessPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-1234567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.REJECTED.getValue(), paymentData, order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getOrder().getStatus());
    }

    @Test
    void testUpdateStatusRejectedPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-1234567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.SUCCESS.getValue(), paymentData, order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), result.getOrder().getStatus());
    }


    @Test
    void testGetPaymentIfIdFound() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF-1234567");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";

        Payment payment = new Payment(paymentId, "BANK", PaymentStatus.SUCCESS.getValue(), paymentData, order);
        doReturn(payment).when(paymentRepository).findById(paymentId);

        Payment result = paymentService.getPayment(paymentId);
        verify(paymentRepository, times(1)).findById(paymentId);

        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfIdNotFound() {

        doReturn(null).when(paymentRepository).findById("hehe");
        assertNull(paymentService.getPayment("hehe"));
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        String paymentId = "348fa642-e16d-4888-92fa-3fc86f8d030c";
        Payment payment = new Payment(paymentId, "VOUCHER", PaymentStatus.SUCCESS.getValue(), paymentData, order);

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        doReturn(paymentList).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();

        verify(paymentRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
    }


}
