package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.Stocks;
import com.kakaopaysec.stock.models.StocksPriceHistory;
import com.kakaopaysec.stock.models.StocksView;
import com.kakaopaysec.stock.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class StocksService {

    private final StocksRepository stocksRepository;
    private final RedisTemplate<String, Long> redisTemplate;

    public List<Stocks> getAll() {
        return stocksRepository.getAll();
    }
    public void deleteById(int id) {
        stocksRepository.deleteById(id);
    }
    public void insert(Stocks stocks) {
        stocksRepository.insert(stocks);
    }

    public StocksPriceHistory getLatestStocksPriceByStockCode (String stockCode) {
        return stocksRepository.getLatestStocksPriceByStockCode(stockCode);
    }

    public void updateViewsCountByStockCode(List<StocksView> stocksView) {
        stocksRepository.updateViewsCountByStockCode(stocksView);
    }

    public void updateStocksPriceHistory(StocksPriceHistory priceHistory) {
        stocksRepository.updateStocksPriceHistory(priceHistory);
    }

    /**
     *
     * @author code-10
     * @param stockCode
     * @return
     */
    public Stocks getByStocksCode(String stockCode) {
        StocksView stocksView;
        Stocks stocks = stocksRepository.getByStockCode(stockCode);
        Long viewsCount = redisTemplate.opsForValue().get(stockCode);

        if (isNull(viewsCount)) {
            stocksView = stocksRepository.getStocksViewByStockCode(stockCode);
            redisTemplate.opsForValue().set(stockCode, stocksView.getViewsCount() + 1);
        } else {
            redisTemplate.opsForValue().set(stockCode, viewsCount + 1);
        }

        return stocks;
    }
}
