package com.kakaopaysec.stock.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersWallet {

    private int id;
    private int userId;
    private String stockCode;
    private int quantity;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
