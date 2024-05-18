package com.example.transactionsservice.service.impl;

import com.example.transactionsservice.dto.NotifRequest;
import com.example.transactionsservice.dto.UpdateBalanceRequest;
import com.example.transactionsservice.exception.*;
import com.example.transactionsservice.feign.AccountInterface;
import com.example.transactionsservice.feign.NotifInterface;
import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;
import com.example.transactionsservice.model.Type;
import com.example.transactionsservice.repository.OperaRepository;
import com.example.transactionsservice.repository.TransRepository;
import com.example.transactionsservice.service.TransService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TansServiceImpl implements TransService {

    private TransRepository transRepository;

    private OperaRepository operaRepository;

    private AccountInterface accountInterface;

    private NotifInterface notifInterface;

    @Override
    public void credit(Long accountNb, double amount , String phone ) throws UpdatingBalanceException, EmptyBodyException, AccountNotFoundException, MessageSendingException {
        boolean resp = this.incrementBalance(accountNb,amount);
        if(resp){
            // if the update is successful than add the trans to the database
            Operation operation = new Operation();
            operation.setAccount_nb(accountNb);
            operation.setAmount(amount);
            operation.setOperation_date(new Date());
            operation.setType(Type.CREDIT);

            operation = operaRepository.save(operation);
            String notificationMessage = "Your recent transaction was successful:\\n" +
                    "🔹 TYPE: CREDIT \\n" +
                    "🔹 Date: " + operation.getOperation_date() + "\\n" +
                    "🔹 Amount: " + operation.getAmount();

            sendNotification(phone,notificationMessage);
        }else{
            throw new UpdatingBalanceException("the process of updating account balance not completed");
        }
    }

    @Override
    public void debit(Long accountNb, double amount , String phone ) throws InsufficientBalanceException, AccountNotFoundException, UpdatingBalanceException, MessageSendingException {

        boolean resp = this.decrementBalance(accountNb,amount);
        if(resp){
            // if the update is successful than add the trans to the database
            Operation operation = new Operation();
            operation.setAccount_nb(accountNb);
            operation.setAmount(amount);
            operation.setOperation_date(new Date());
            operation.setType(Type.DEBIT);

            operation = operaRepository.save(operation);
            String notificationMessage = "Your recent transaction was successful:\\n" +
                    "🔹 TYPE: DEBIT \\n" +
                    "🔹 Date: " + operation.getOperation_date() + "\\n" +
                    "🔹 Amount: " + operation.getAmount();

            sendNotification(phone,notificationMessage);
        }else{
            throw new UpdatingBalanceException("the process of updating account balance not completed");
        }
    }

    @Override
    public void transfer(Long sender, Long recipient, double amount, String phone ) throws EmptyBodyException, AccountNotFoundException, InsufficientBalanceException, UpdatingBalanceException, MessageSendingException {
        boolean resp1 = incrementBalance(recipient,amount);
        boolean resp2 = decrementBalance(sender,amount);

        if(resp1 && resp2){
            // if both operations succeeded we save it to DB
            Transfer transfer = new Transfer();
            transfer.setAmount(amount);
            transfer.setSender_acc(sender);
            transfer.setRecipient_acc(recipient);
            transfer.setOperation_date(new Date());

            transfer = transRepository.save(transfer);
            String notificationMessage = "Your recent transaction was successful:\\n" +
                    "🔹 TYPE: TRANSFER \\n" +
                    "🔹 Date: " + transfer.getOperation_date() + "\\n" +
                    "🔹 Amount: " + transfer.getAmount() + "\\n" +
                    "🔹 TO: " + transfer.getRecipient_acc();


            sendNotification(phone,notificationMessage);

        }else{
            throw new UpdatingBalanceException("the process of updating accounts balance not completed");
        }
    }

    @Override
    public boolean incrementBalance(Long accountNb , double amount) throws AccountNotFoundException, EmptyBodyException {
        ResponseEntity<?> response = accountInterface.getAccountBalance(accountNb);

        if(response.getStatusCode().is2xxSuccessful()){
            if (response.getBody() != null){
                // Adding the amount to prev balance
                double prevBalance = (double) response.getBody();
                double newBalance = prevBalance + amount;
                return this.updateBalance(accountNb,newBalance);
            }else{
                throw new EmptyBodyException("Empty Body !");
            }
        }else{
            throw new AccountNotFoundException("Account with nb : " + accountNb + " Not found");
        }

    }

    @Override
    public boolean decrementBalance(Long accountNb , double amount) throws AccountNotFoundException, InsufficientBalanceException {
        ResponseEntity<?> response = accountInterface.getAccountBalance(accountNb);

        if (response.getStatusCode().is2xxSuccessful()){
            if ( response.getBody() != null && (double) response.getBody() >= amount){
                // Adding the amount to prev balance
                double prevBalance = (double) response.getBody();
                double newBalance = prevBalance - amount;

                return this.updateBalance(accountNb,newBalance);
            }else{
                throw new InsufficientBalanceException("Insufficient account Balance");
            }
        }else{
            throw new AccountNotFoundException("Account with nb : " + accountNb + " Not found");
        }
    }

    public void sendNotification(String phone , String message) throws MessageSendingException {
        NotifRequest request = new NotifRequest(
                phone,
                message
        );

        ResponseEntity<?> response = notifInterface.sendMessage(request);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new MessageSendingException("A problem was acqurred when sending message");
        }
    }
    @Override
    public boolean updateBalance(Long accountNb, double newBalance) {
        UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest(accountNb, newBalance);
        ResponseEntity<?> response = accountInterface.updateBalance(updateBalanceRequest);

        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public List<Operation> getOpHistory(Long account_nb) {
        return operaRepository.findAll();
    }

    @Override
    public List<Transfer> getTransHistory(Long account_nb) {
        return transRepository.findAll();
    }
}
