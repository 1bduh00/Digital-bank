package com.example.transactionsservice.service;

import com.example.transactionsservice.exception.*;
import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;
import com.example.transactionsservice.model.Type;

import java.util.List;

public interface TransService {

    void credit(Long accountNb , double amount , String phone ) throws AccountNotFoundException, EmptyBodyException, UpdatingBalanceException, MessageSendingException;
    void debit(Long accountNb , double amount , String phone ) throws InsufficientBalanceException, AccountNotFoundException, UpdatingBalanceException, MessageSendingException;
    void transfer(Long sender , Long recipient , double amount , String phone ) throws EmptyBodyException, AccountNotFoundException, InsufficientBalanceException, UpdatingBalanceException, MessageSendingException;
    boolean decrementBalance(Long accountNb , double amount) throws AccountNotFoundException, InsufficientBalanceException;
    boolean incrementBalance(Long accountNb , double amount) throws AccountNotFoundException, EmptyBodyException;
    boolean updateBalance(Long accountNb , double newBalance);
    List<Operation> getOpHistory(Long account_nb);
    List<Transfer> getTransHistory(Long account_nb);
}
