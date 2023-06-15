package com.kakaopaysec.stock.utils;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class Pageable {

    private int limit;
    private int offset;
    private String category;
    private String ascending = "DESC";

    public Pageable(int limit, int offset, String category) {
        this.limit = limit;
        this.offset = offset;
        switch (category) {
            case "popularity":
                this.category = "VIEWS_COUNT";
                break;
            case "rise":
                this.category = "PERCENTAGE_PRICE";
                break;
            case "fall":
                this.category = "PERCENTAGE_PRICE";
                this.ascending = "ASC";
                break;
            case "volume":
                this.category = "TRANSACTION_VOLUME";
                break;
            default:
                this.category = "STOCK_CODE";
                break;
        }
    }

    public static Pageable of(int limit, int offset, String category) {
        return new Pageable(limit, offset, category);
    }
}