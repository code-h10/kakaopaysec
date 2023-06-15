package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.StocksPriceHistory;
import com.kakaopaysec.stock.models.Transactions;
import com.kakaopaysec.stock.models.UsersWallet;
import com.kakaopaysec.stock.repository.StocksRepository;
import com.kakaopaysec.stock.repository.TransactionRepository;
import com.kakaopaysec.stock.repository.UsersWalletRepository;
import com.kakaopaysec.stock.utils.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock private TransactionRepository transactionRepository;
    @Mock private UsersWalletRepository usersWalletRepository;
    @Mock private StocksRepository stocksRepository;
    @InjectMocks private TransactionService transactionService;

    Transactions transaction;
    UsersWallet usersWallet;
    StocksPriceHistory stocksPriceHistory;

    @BeforeEach
    public void setUp() {

        transaction = new Transactions();
        transaction.setUserId(1);
        transaction.setStockCode("00001");
        transaction.setType(TransactionType.SELL.getType());
        transaction.setQuantity(10);
        transaction.setPrice(24000);

        usersWallet = new UsersWallet();
        usersWallet.setUserId(transaction.getUserId());
        usersWallet.setStockCode(transaction.getStockCode());
        usersWallet.setQuantity(30);

        stocksPriceHistory = new StocksPriceHistory();
        stocksPriceHistory.setStockCode(transaction.getStockCode());
        stocksPriceHistory.setCurrentPrice(23500);
        stocksPriceHistory.setTransactionVolume(Long.valueOf("100"));

    }

    @Test
    public void testSellTransactionWithWallet() {


        transaction.setType(TransactionType.SELL.getType());

        // UsersWallet Mock 객체 반환 설정
        when(usersWalletRepository.getByUserIdAndStockCode(transaction.getUserId(), transaction.getStockCode())).thenReturn(usersWallet);

        /* StocksPriceHistory Mock 객체 반환 설정 */
        when(stocksRepository.getLatestStocksPriceByStockCode(transaction.getStockCode())).thenReturn(stocksPriceHistory);

        // insert() 메서드 호출
        transactionService.insert(transaction);

        // 테스트 결과
        verify(usersWalletRepository, times(1)).updateQuantityByUserIdAndStockCode(any(UsersWallet.class));
        verify(transactionRepository, times(1)).insert(transaction);
        verify(stocksRepository, times(1)).updateStocksPriceHistory(any(StocksPriceHistory.class));
    }


    @Test
    public void testSellTransactionWithOutWallet() {

        // UsersWallet Null 반환 설정
        when(usersWalletRepository.getByUserIdAndStockCode(transaction.getUserId(), transaction.getStockCode())).thenReturn(null);

        // 테스트 결과 검증
        Exception exception = assertThrows(NullPointerException.class, () -> transactionService.insert(transaction));
        assertEquals("소지하고 있는 해당 주식이 없거나, 현재 소지하고 있는 수량이 매도 수량 보다 부족합니다. 확인 후 다시 시도해주세요.", exception.getMessage());
    }


    @Test
    public void testBuyTransactionWithOutWallet() {

        // 테스트할 Transaction Type 설정
        transaction.setType(TransactionType.BUY.getType());

        // UsersWallet Mock 객체 반환 설정
        when(usersWalletRepository.getByUserIdAndStockCode(transaction.getUserId(), transaction.getStockCode())).thenReturn(usersWallet);

        /* StocksPriceHistory Mock 객체 반환 설정 */
        when(stocksRepository.getLatestStocksPriceByStockCode(transaction.getStockCode())).thenReturn(stocksPriceHistory);

        // insert() 메서드 호출
        transactionService.insert(transaction);

        // 테스트 결과 검증
        verify(usersWalletRepository, times(1)).updateQuantityByUserIdAndStockCode(any(UsersWallet.class));
        verify(usersWalletRepository, times(0)).insert(any(UsersWallet.class));
        verify(transactionRepository, times(1)).insert(transaction);
        verify(stocksRepository, times(1)).updateStocksPriceHistory(any(StocksPriceHistory.class));


    }

    @Test
    public void testBuyTransactionWithWallet() {

        // 테스트할 Transaction Type 설정
        transaction.setType(TransactionType.BUY.getType());

        // UsersWallet Mock 객체 반환 설정
        when(usersWalletRepository.getByUserIdAndStockCode(transaction.getUserId(), transaction.getStockCode())).thenReturn(null);

        /* StocksPriceHistory Mock 객체 반환 설정 */
        when(stocksRepository.getLatestStocksPriceByStockCode(transaction.getStockCode())).thenReturn(stocksPriceHistory);

        // insert() 메서드 호출
        transactionService.insert(transaction);

        // 테스트 결과 검증
        verify(usersWalletRepository, times(0)).updateQuantityByUserIdAndStockCode(any(UsersWallet.class));
        verify(usersWalletRepository, times(1)).insert(any(UsersWallet.class));
        verify(transactionRepository, times(1)).insert(transaction);
        verify(stocksRepository, times(1)).updateStocksPriceHistory(any(StocksPriceHistory.class));


    }
}