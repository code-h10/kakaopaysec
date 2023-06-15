package com.kakaopaysec.stock.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Stocks {
    private int id;
    private String stockCode;
    private String stockName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
