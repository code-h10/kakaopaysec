package com.kakaopaysec.stock.repository;

import com.kakaopaysec.stock.models.Stocks;
import com.kakaopaysec.stock.models.StocksPriceHistory;
import com.kakaopaysec.stock.models.StocksView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StocksRepository {

    List<Stocks> getAll();
    List<StocksPriceHistory> getStocksPriceHistoryAll();
    StocksView getStocksViewByStockCode(String stockCode);
    void updateViewsCountByStockCode(List<StocksView> stocksView);
    StocksPriceHistory getLatestStocksPriceByStockCode(String stockCode);

    void updateCurrentPriceByStockCode(List<StocksPriceHistory> stocksPriceHistories);
    void updateStocksPriceHistory(StocksPriceHistory stocksPriceHistory);
    void insert(Stocks stock);
    void deleteById(int id);
    Stocks getByStockCode(String stockCode);

}
