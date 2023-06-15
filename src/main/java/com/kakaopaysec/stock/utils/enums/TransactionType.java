package com.kakaopaysec.stock.utils.enums;

public enum TransactionType {

    BUY(0, "BUY"), SELL(1, "SELL");

    private int type;
    private String description;
    TransactionType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionType fromType(int type) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.type == type) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Invalid Transaction Type: " + type);
    }

    @Override
    public String toString() {
        return description;
    }
}
