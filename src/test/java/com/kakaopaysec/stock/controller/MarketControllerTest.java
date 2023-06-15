package com.kakaopaysec.stock.controller;

import com.kakaopaysec.stock.models.Market;
import com.kakaopaysec.stock.service.MarketService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MarketController.class)
class MarketControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private MarketService marketService;

    private List<Market> marketList;

    @BeforeEach
    public void setUp() {

        Market market = new Market();
        marketList = new ArrayList<>();
        market.setCurrentPrice(30100);
        market.setClosePrice(30100);
        market.setTransactionVolume(Long.parseLong("5394420050"));
        market.setStockName("KODEX 200");
        market.setDiffPrice(19850);
        market.setYesterdayClosePrice(0);
        market.setOpenPrice(10250);
        market.setViewsCount(0);
        market.setStockCode("069500");
        market.setPercentagePrice(new BigDecimal("193.66"));
        marketList.add(market);
    }

    @DisplayName("실시간 순위 응답데이터 테스트")
    @Test
    public void testGetStocks() throws Exception {

        when(marketService.getStocks(any())).thenReturn(marketList);

        mockMvc.perform(get("/api/v1/market/stocks/{category}", "rise")
                        .queryParam("limit", "100")
                        .queryParam("offset", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].stockName", equalTo("KODEX 200")))
                .andExpect(jsonPath("$[0].closePrice", equalTo(30100)))
                .andExpect(jsonPath("$[0].openPrice", equalTo(10250)))
                .andExpect(jsonPath("$[0].stockCode", equalTo("069500")))
                .andExpect(jsonPath("$[0].currentPrice", equalTo(30100)))
                .andExpect(jsonPath("$[0].transactionVolume").value(Long.parseLong("5394420050")))
                .andExpect(jsonPath("$[0].percentagePrice").value(new BigDecimal("193.66")));

    }

}