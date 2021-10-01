package com.example.exoonepay2;

import com.example.exoonepay2.entities.OrderLine;
import com.example.exoonepay2.entities.Transaction;
import com.example.exoonepay2.exception.AppException;
import com.example.exoonepay2.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest
public class TransactionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionTest.class);

    @Autowired
    TransactionService transactionService;


    /**
     * test get all transaction
     */
    @Test
    public void testGetAllTransaction() {

        List<Transaction> transactions = transactionService.findAll();

        assertTrue(transactions.size() > 0);
    }

    /**
     * test get transaction by ID
     */
    @Test
    public void testGetTransactionById (){
        Integer id = 10;
        Transaction transaction = transactionService.findById(10);

        assertTrue(transaction.getId().equals(id));
    }


    /**
     * test add transaction
     */
    @Test
    public void testCreateTransaction() {
        /**
         * - create transaction for an amount of 54,80 EUR with CREDIT_CARD
         * and an order containing 4 pairs of ski gloves at 10 EUR,
         * and 1 woolen hat at 14.80
         */
        BigDecimal bigDecimal = BigDecimal.valueOf(54.80);
        BigDecimal gantPrice = BigDecimal.valueOf(10.00);
        BigDecimal bonnetPrice = BigDecimal.valueOf(14.80);

        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(OrderLine.builder().productName("ski gloves")
                .price(gantPrice).quantity(4).build());
        orderLines.add(OrderLine.builder().productName("woolen hat")
                .price(bonnetPrice).quantity(1).build());

        Transaction transaction = Transaction.builder()
                .amount(bigDecimal)
                .paymentType(Transaction.PaymentType.CREDIT_CARD)
                .orderLineList(orderLines)
                .build();

        boolean isOK = true;
        try {
            Transaction tOut = transactionService.save(transaction);
            if (tOut == null || tOut.getId() == null) {
                isOK = false;
            }
        } catch (AppException e) {
            LOGGER.error(e.getMessage(), e);
            isOK = false;
        }
        assertTrue(isOK);
    }

    /**
     * test add transaction with PAYPAL
     */
    @Test
    public void testCreateTransactionWithPaypal() {

        /**
         * - create transaction for an amount of 208 EUR with PAYPAL
         *  and an order containing 1 bike at 208 EUR
         */

        boolean isOK = true;
        try {
            BigDecimal bigDecimal = BigDecimal.valueOf(208.00);

            List<OrderLine> orderLines = new ArrayList<>();
            orderLines.add(OrderLine.builder().productName("bike")
                    .price(bigDecimal).quantity(1).build());

            Transaction transaction = Transaction.builder()
                    .amount(bigDecimal)
                    .paymentType(Transaction.PaymentType.PAYPAL)
                    .status(Transaction.Status.NEW)
                    .orderLineList(orderLines)
                    .build();

            Transaction tOut = transactionService.save(transaction);
            if (tOut == null || tOut.getId() == null) {
                isOK = false;
            }
        } catch (AppException e) {
            LOGGER.error(e.getMessage(), e);
            isOK = false;
        }
        assertTrue(isOK);
    }

    /**
     * test create transaction with status NEW OK
     */
    @Test
    public void testCreateTransactionStatusNewOk(){

        boolean isOk = true;
            try {
                Transaction transaction = Transaction.builder().status(Transaction.Status.NEW).build();
                transactionService.save(transaction);

            } catch (AppException e) {
                isOk = false;
                System.out.println(e.getMessage());
            }

            assertTrue(isOk);
    }

    /**
     * test create transaction with status NEW KO
     */
    @Test
    public void testCreateTransactionStatusNewKo(){

        boolean isOk = true;
        try {
            Transaction transaction = Transaction.builder().status(Transaction.Status.AUTHORIZED).build();
            transactionService.save(transaction);

        } catch (AppException e) {
            isOk = false;
            System.out.println(e.getMessage());
        }

        assertTrue(!isOk);
    }

    /**
     * test update transaction KO
     */
    @Test
    public void testUpdateTransactionFromStatusNewToCapturedShouldBeKo() {

        /**
         * - update the transaction by passing the status to CAPTURED
         */

        boolean isOK = true;
        try {
            Transaction transaction = Transaction.builder().status(Transaction.Status.NEW).build();
            transaction = transactionService.save(transaction);
            transaction.setStatus(Transaction.Status.CAPTURED);

           transactionService.save(transaction);

        } catch (Exception e) {
            isOK = false;
            System.out.println(e.getMessage());
        }

        assertTrue(!isOK);

    }

    /**
     * test update transaction OK
     */
    @Test
    public void testUpdateTransactionFromStatusAuthorizedToCapturedShouldBeOk() {

        /**
         * - update the transaction by passing the status to AUTHORIZED then CAPTURED
         */
        Transaction transaction = null;
        boolean isOK = true;
        try {
            transaction = Transaction.builder().status(Transaction.Status.NEW).build();
            transaction = transactionService.save(transaction);
            transaction.setStatus(Transaction.Status.AUTHORIZED);
            transaction = transactionService.save(transaction);
            transaction.setStatus(Transaction.Status.CAPTURED);
            transaction = transactionService.save(transaction);
        } catch (Exception e) {
            isOK = false;
            System.out.println(e.getMessage());
        }

        assertTrue(isOK && transaction !=null && Transaction.Status.CAPTURED.equals(transaction.getStatus()));

    }

}
