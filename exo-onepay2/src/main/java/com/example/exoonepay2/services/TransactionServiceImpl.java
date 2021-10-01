package com.example.exoonepay2.services;

import com.example.exoonepay2.dao.TransactionRepository;
import com.example.exoonepay2.entities.Transaction;
import com.example.exoonepay2.exception.AppException;
import com.example.exoonepay2.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * Find by id
     *
     * @param id
     * @return
     */
    @Override
    public Transaction findById(Integer id) {

        Optional optTransaction = transactionRepository.findById(id);

        if (optTransaction.isPresent()) {
            return (Transaction) optTransaction.get();
        } else {
            throw new NotFoundException("Transaction not found with id " + id);
        }
    }

    /**
     * Find all Transaction
     *
     * @return
     */
    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    /**
     * Save a new transaction with status
     *
     * @param transaction
     * @return
     * @throws AppException
     */
    @Override
    public Transaction save(Transaction transaction) throws AppException {
        Transaction transactionOld = null;
        if (transaction.getId() != null) {
            Optional<Transaction> optT = transactionRepository.findById(transaction.getId());
            transactionOld = optT.isPresent() ? optT.get() : null;
        }
        transaction = checkStatus(transactionOld, transaction);
        return transactionRepository.save(transaction);
    }

    /**
     * Checked the transaction status
     *
     * @param transactionOld
     * @param transaction
     * @return
     * @throws AppException
     */
    public Transaction checkStatus(Transaction transactionOld, Transaction transaction) throws AppException {
        if (transactionOld == null && transaction.getStatus() == null) {
            transaction.setStatus(Transaction.Status.NEW);
        }
        if (transactionOld == null &&
                !Transaction.Status.NEW.equals(transaction.getStatus())) {
            throw new AppException("Status must be New for insert : " + transaction);
        }


        /**
         * if transaction equals AUTHORIZED then transaction can to be CAPTURED
         */
        if (Transaction.Status.CAPTURED.equals(transaction.getStatus()) &&
                !Transaction.Status.AUTHORIZED.equals(transactionOld.getStatus()) &&
                !Transaction.Status.CAPTURED.equals(transactionOld.getStatus())) {

            throw new AppException("Status Captured is available only from authorized for id : " + transactionOld.getId());
        }
        /**
         * if transaction equals CAPTURED then it is not editable
         */
        if (transactionOld != null && Transaction.Status.CAPTURED.equals(transactionOld.getStatus())
                && !Transaction.Status.CAPTURED.equals(transaction.getStatus())) {
            throw new AppException("Status CAPTURED is not editable for id: " + transactionOld.getId());
        }


        return transaction;
    }

}
