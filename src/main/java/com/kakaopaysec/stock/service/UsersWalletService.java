package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.UsersWallet;
import com.kakaopaysec.stock.repository.UsersWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersWalletService {

    private final UsersWalletRepository usersWalletRepository;

    public List<UsersWallet> getByUserId(int userId) {
        return usersWalletRepository.getByUserId(userId);
    }

    public UsersWallet getByUserIdAndStockCode(int userId, String stockCode) {
        return usersWalletRepository.getByUserIdAndStockCode(userId, stockCode);
    }

}
