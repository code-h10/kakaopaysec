package com.kakaopaysec.stock.models;

import com.kakaopaysec.stock.utils.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private int id;
    private int type;
    private String stockCode;
    private int userId;
    private int quantity;
    private int price;
    private BigDecimal fee;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
