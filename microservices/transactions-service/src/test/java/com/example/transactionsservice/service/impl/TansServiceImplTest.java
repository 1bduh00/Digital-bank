package com.example.transactionsservice.service.impl;

import com.example.transactionsservice.dto.UpdateBalanceRequest;
import com.example.transactionsservice.feign.AccountInterface;
import com.example.transactionsservice.feign.NotifInterface;
import com.example.transactionsservice.repository.OperaRepository;
import com.example.transactionsservice.repository.TransRepository;
import com.example.transactionsservice.service.TransService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TansServiceImplTest {
    private TransService transService;
    @Mock
    private OperaRepository operaRepository;
    @Mock
    private TransRepository transRepository;
    @Mock
    private AccountInterface accountInterface;
    private NotifInterface notifInterface;

    @BeforeEach
    public void setup(){
        transService = new TansServiceImpl(transRepository,operaRepository,accountInterface,notifInterface);
    }

    @Test
    void testCredit_Successful() {
        // Mocking account balance response
        Long accountNb = 123456789L;
        double amount = 100.0;



    }

    @Test
    void debit() {
    }

    @Test
    void transfer() {
    }

    @Test
    void shouldIncrementBalance() {
        // Mocking account balance response with body
        Long accountNb = 123456789L;
        double amount = 100.0;

        when(accountInterface.getAccountBalance(accountNb)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        // Mocking update balance response
        when(transService.updateBalance(accountNb, 600.0)).thenReturn(true);
        // Executing incrementBalance method
        assertDoesNotThrow(() -> transService.incrementBalance(accountNb, amount));
        // Verifying update balance called with correct parameters
        verify(transService, times(1)).updateBalance(accountNb, 600.0);

    }

    @Test
    void decrementBalance() {
    }

    @Test
    void testUpdateBalance_Successful() {

        // Mocking update balance request
        Long accountNb = 123456789L;
        double newBalance = 1000.0;
        UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest(accountNb, newBalance);

        when(accountInterface.updateBalance(updateBalanceRequest)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        // Executing updateBalance method
        boolean result = transService.updateBalance(accountNb, newBalance);
        // Verifying result
        assertTrue(result);

    }

    @Test
    void getOpHistory() {
    }

    @Test
    void getTransHistory() {
    }
}