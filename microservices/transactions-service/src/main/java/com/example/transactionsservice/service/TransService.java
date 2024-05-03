package com.example.transactionsservice.service;

import com.example.transactionsservice.exception.AccountNotFoundException;
import com.example.transactionsservice.exception.EmptyBodyException;
import com.example.transactionsservice.exception.InsufficientBalanceException;
import com.example.transactionsservice.exception.UpdatingBalanceException;
import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;
import com.example.transactionsservice.model.Type;

import java.util.List;

public interface TransService {

    void credit(Long accountNb , double amount) throws AccountNotFoundException, EmptyBodyException, UpdatingBalanceException;
    void debit(Long accountNb , double amount) throws InsufficientBalanceException, AccountNotFoundException, UpdatingBalanceException;
    void transfer(Long sender , Long recipient , double amount) throws EmptyBodyException, AccountNotFoundException, InsufficientBalanceException, UpdatingBalanceException;
    boolean decrementBalance(Long accountNb , double amount) throws AccountNotFoundException, InsufficientBalanceException;
    boolean incrementBalance(Long accountNb , double amount) throws AccountNotFoundException, EmptyBodyException;
    boolean updateBalance(Long accountNb , double newBalance);
    List<Operation> getOpHistory(Long account_nb);
    List<Transfer> getTransHistory(Long account_nb);
}
