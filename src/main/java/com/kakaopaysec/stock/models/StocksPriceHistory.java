package com.kakaopaysec.stock.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StocksPriceHistory {

    private int id;
    private String stockCode;
    private int yesterdayClosePrice;
    private int openPrice;
    private int closePrice;
    private int highPrice;
    private int lowPrice;
    private int currentPrice;
    private Long transactionVolume;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
