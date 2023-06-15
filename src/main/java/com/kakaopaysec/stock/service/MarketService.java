package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.Market;
import com.kakaopaysec.stock.models.StocksPriceHistory;
import com.kakaopaysec.stock.models.StocksView;
import com.kakaopaysec.stock.repository.MarketRepository;
import com.kakaopaysec.stock.repository.StocksRepository;
import com.kakaopaysec.stock.utils.CalculateUtils;
import com.kakaopaysec.stock.utils.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final StocksRepository stocksRepository;


    public List<Market> getStocks(Pageable pageable) {
        return marketRepository.getStocks(pageable);
    }

    public void changeStocksDataRandom() {

        List<StocksPriceHistory> priceHistories = stocksRepository.getStocksPriceHistoryAll();

        StocksPriceHistory updatedPriceHistory;
        StocksView updatedStocksView;

        List<StocksView> updatedStocksViews = new ArrayList<>();
        List<StocksPriceHistory> updatedPriceHistories = new ArrayList<>();
        Random random = new Random();

        for (StocksPriceHistory priceHistory : priceHistories) {

            int currentPrice = CalculateUtils.calculateRandomFluctuation(priceHistory.getCurrentPrice());
            Long transactionVolume = CalculateUtils.calculateRandomFluctuation(priceHistory.getTransactionVolume());
            long viewsCount = CalculateUtils.calculateRandomFluctuation(random.nextInt(10001));

            updatedPriceHistory = new StocksPriceHistory();
            updatedPriceHistory.setId(priceHistory.getId());
            updatedPriceHistory.setStockCode(priceHistory.getStockCode());
            updatedPriceHistory.setCurrentPrice(currentPrice);
            updatedPriceHistory.setTransactionVolume(transactionVolume);
            updatedPriceHistories.add(updatedPriceHistory);

            updatedStocksView = new StocksView();
            updatedStocksView.setStockCode(priceHistory.getStockCode());
            updatedStocksView.setViewsCount(viewsCount);
            updatedStocksViews.add(updatedStocksView);

        }

        stocksRepository.updateViewsCountByStockCode(updatedStocksViews);
        stocksRepository.updateCurrentPriceByStockCode(updatedPriceHistories);
    }
}
