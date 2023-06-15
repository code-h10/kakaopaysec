package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.StocksPriceHistory;
import com.kakaopaysec.stock.models.Transactions;
import com.kakaopaysec.stock.models.UsersWallet;
import com.kakaopaysec.stock.repository.StocksRepository;
import com.kakaopaysec.stock.repository.TransactionRepository;
import com.kakaopaysec.stock.repository.UsersWalletRepository;
import com.kakaopaysec.stock.utils.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UsersWalletRepository usersWalletRepository;
    private final StocksRepository stocksRepository;

    public List<Transactions> getAll() {
        return transactionRepository.getAll();
    }
    public List<Transactions> getByUserId(int userId) {
        return transactionRepository.getByUserId(userId);
    }
    public List<Transactions> getByStockCode(String stockCode) { return transactionRepository.getByStockCode(stockCode); }
    public Transactions getById(int id) {
        return transactionRepository.getById(id);
    }
    public List<Transactions> getByType(int type) {
        return transactionRepository.getByType(type);
    }


    /**
     * @author code-10
     * @param transaction
     */
    @Transactional
    public void insert(Transactions transaction) {

        UsersWallet usersWallet = usersWalletRepository.getByUserIdAndStockCode(transaction.getUserId(), transaction.getStockCode());
        StocksPriceHistory prevStocksPriceHistory = stocksRepository.getLatestStocksPriceByStockCode(transaction.getStockCode());
        UsersWallet newWallet = new UsersWallet();

        if (TransactionType.fromType(transaction.getType()) == TransactionType.BUY) {
            if (isNull(usersWallet)) {
                newWallet.setUserId(transaction.getUserId());
                newWallet.setQuantity(transaction.getQuantity());
                newWallet.setStockCode(transaction.getStockCode());
                usersWalletRepository.insert(newWallet);
            } else {
                newWallet.setUserId(transaction.getUserId());
                newWallet.setQuantity(transaction.getQuantity() + usersWallet.getQuantity());
                newWallet.setStockCode(transaction.getStockCode());
                usersWalletRepository.updateQuantityByUserIdAndStockCode(newWallet);
            }
        } else {
            if (isNull(usersWallet) || usersWallet.getQuantity() < transaction.getQuantity()) {
                throw new NullPointerException("소지하고 있는 해당 주식이 없거나, 현재 소지하고 있는 수량이 매도 수량 보다 부족합니다. 확인 후 다시 시도해주세요.");
            } else {
                newWallet.setUserId(transaction.getUserId());
                newWallet.setQuantity(usersWallet.getQuantity() - transaction.getQuantity());
                newWallet.setStockCode(transaction.getStockCode());
                usersWalletRepository.updateQuantityByUserIdAndStockCode(newWallet);
            }
        }

        /* 체결된 거래 정보 저장 */
        BigDecimal fee = new BigDecimal(transaction.getPrice()).multiply(new BigDecimal("0.015")).setScale(3, RoundingMode.HALF_UP);
        transaction.setFee(fee);
        transactionRepository.insert(transaction);

        /* 가중평균 값을 구하여 체결된 해당 종목의 현재가에 반영 */
        int currentPrice =  calculateWeightedAverage(prevStocksPriceHistory.getCurrentPrice(), prevStocksPriceHistory.getTransactionVolume(), (transaction.getPrice() * transaction.getQuantity()), transaction.getQuantity());
        StocksPriceHistory currentStockPriceHistory = new StocksPriceHistory();
        currentStockPriceHistory.setId(prevStocksPriceHistory.getId());
        currentStockPriceHistory.setCurrentPrice(currentPrice);
        currentStockPriceHistory.setStockCode(transaction.getStockCode());
        currentStockPriceHistory.setOpenPrice(prevStocksPriceHistory.getOpenPrice());
        currentStockPriceHistory.setClosePrice(prevStocksPriceHistory.getClosePrice());
        currentStockPriceHistory.setLowPrice(Math.min(prevStocksPriceHistory.getLowPrice(), currentPrice));
        currentStockPriceHistory.setHighPrice(Math.max(prevStocksPriceHistory.getHighPrice(), currentPrice));
        currentStockPriceHistory.setTransactionVolume(prevStocksPriceHistory.getTransactionVolume() + transaction.getQuantity());
        stocksRepository.updateStocksPriceHistory(currentStockPriceHistory);

    }

    /**
     * 가중평규치 계산
     * @param prevCurrentPrice 체결전 해당 종목 현재가
     * @param prevTransactionVolume 체결전 해당 종목 거래량
     * @param transactionPrice 현재 체결된 거래가
     * @param transactionVolume 현재 체결된 거래량
     * @return
     */
    private int calculateWeightedAverage(int prevCurrentPrice, Long prevTransactionVolume, int transactionPrice, int transactionVolume) {
        return (int) (prevCurrentPrice * prevTransactionVolume + transactionPrice * transactionVolume / (prevTransactionVolume + transactionVolume));
    }
}
