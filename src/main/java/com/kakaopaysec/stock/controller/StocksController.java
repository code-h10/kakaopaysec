package com.kakaopaysec.stock.controller;

import com.kakaopaysec.stock.models.Stocks;
import com.kakaopaysec.stock.models.StocksPriceHistory;
import com.kakaopaysec.stock.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/test")
public class StocksController {

    private final StocksService stocksService;

    /**
     * - 주식 종목 상세 조회
     * - 인기 탭의 종목 정렬에 사용
     * @author code-10
     * @param stockCode
     * @return
     */
    @GetMapping("/stocks/{stockCode}")
    public ResponseEntity<Stocks> getByStocksCode(@PathVariable String stockCode) throws Exception {
        return new ResponseEntity<>(stocksService.getByStocksCode(stockCode), HttpStatus.OK);
    }

    /**
     * 일별 주식 데이터 수정
     * @author code-10
     * @param priceHistory
     */
    @PutMapping("/stocks/history")
    public ResponseEntity<StocksPriceHistory> updateStocksPriceHistory(@RequestBody StocksPriceHistory priceHistory) {
        stocksService.updateStocksPriceHistory(priceHistory);
        return new ResponseEntity<>(priceHistory, HttpStatus.OK);
    }

    /**
     * 일별 주식 데이터 조회
     * @author code-10
     * @param stockCode
     */
    @GetMapping("/stocks/{stockCode}/history")
    public ResponseEntity<StocksPriceHistory> getLatestStocksPriceByStockCode(@PathVariable String stockCode) {
        return new ResponseEntity<>(stocksService.getLatestStocksPriceByStockCode(stockCode), HttpStatus.OK);
    }

}
