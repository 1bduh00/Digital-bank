package com.example.account_managementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBalanceRequest {

    private Long account_nb;

    private double balance;

}
