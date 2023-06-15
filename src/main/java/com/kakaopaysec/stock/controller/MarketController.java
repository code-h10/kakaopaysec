package com.kakaopaysec.stock.controller;

import com.kakaopaysec.stock.models.Market;
import com.kakaopaysec.stock.service.MarketService;
import com.kakaopaysec.stock.utils.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/market")
public class MarketController {

    private final MarketService marketService;

    /**
     * 실시간 우선 순위 서비스 Restful API
     * @author code-10
     * @param category
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/stocks/{category}")
    public ResponseEntity<List<Market>> getStocksByTab(@PathVariable String category,
                                                      @RequestParam(required = false, defaultValue = "100") int limit,
                                                      @RequestParam(required = false, defaultValue = "0") int offset) throws Exception {
        return ResponseEntity.ok(marketService.getStocks(Pageable.of(limit, offset, category)));
    }

    /**
     * 호출 시 주식 종목과 관련 데이터를 랜덤으로 변경할 수 있는 테스트용 API
     * @author code-10
     * @return
     */
    @GetMapping("/stocks/random")
    public ResponseEntity<Void> changeStocksDataRandom() {
        marketService.changeStocksDataRandom();
        return ResponseEntity.ok().build();
    }


}
