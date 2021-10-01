package com.example.exoonepay2.services;

import com.example.exoonepay2.entities.Transaction;
import com.example.exoonepay2.exception.AppException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    Transaction findById(Integer id);

    List<Transaction> findAll();

    Transaction save(Transaction transaction) throws AppException;

}
