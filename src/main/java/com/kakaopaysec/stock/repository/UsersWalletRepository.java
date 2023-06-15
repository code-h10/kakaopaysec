package com.kakaopaysec.stock.repository;

import com.kakaopaysec.stock.models.UsersWallet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersWalletRepository {

    List<UsersWallet> getAll();
    List<UsersWallet> getByUserId(int userId);
    UsersWallet getByUserIdAndStockCode(int userId, String stockCode);
    void updateQuantityByUserIdAndStockCode(UsersWallet wallet);
    void insert(UsersWallet wallet);

}
