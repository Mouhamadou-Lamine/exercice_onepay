package com.example.exoonepay2.controller;

import com.example.exoonepay2.entities.Transaction;
import com.example.exoonepay2.exception.AppException;
import com.example.exoonepay2.services.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionServiceImpl transactionService;

    /**
     * get by ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Transaction getById(@PathVariable Integer id){
        return transactionService.findById(id);
    }

    /**
     * get all transaction
     * @return
     */
    @GetMapping
    List<Transaction> getAll() {
        return transactionService.findAll();
    }

    /**
     * add transaction
     * @param transaction
     * @return
     */
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) throws AppException {
        try {
            return transactionService.save(transaction);
        } catch (AppException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * update transaction
     * @param transaction
     * @return
     */
    @PutMapping
    public Transaction updateTransaction(
                        @RequestBody Transaction transaction) throws AppException {

        try {
            return transactionService.save(transaction);
        } catch (AppException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

    }

}
