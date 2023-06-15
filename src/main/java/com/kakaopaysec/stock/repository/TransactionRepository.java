package com.kakaopaysec.stock.repository;

import com.kakaopaysec.stock.models.Transactions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TransactionRepository {

    void insert(Transactions transaction);
    List<Transactions> getByUserId(int userId);
    List<Transactions> getByType(int type);
    List<Transactions> getByStockCode(String stockCode);
    Transactions getById(int id);
    List<Transactions> getAll();
}
