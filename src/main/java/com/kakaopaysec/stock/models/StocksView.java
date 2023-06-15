package com.kakaopaysec.stock.models;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class StocksView {

    private int id;
    private String stockCode;
    private Long viewsCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
