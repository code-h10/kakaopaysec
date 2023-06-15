package com.kakaopaysec.stock.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Market {

    private int id;
    private String stockCode;
    private String stockName;
    private int diffPrice;
    private BigDecimal percentagePrice;
    private int yesterdayClosePrice;
    private int currentPrice;
    private int openPrice;
    private int closePrice;
    private int viewsCount;
    private Long transactionVolume;
}
