package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.Market;
import com.kakaopaysec.stock.models.Stocks;
import com.kakaopaysec.stock.repository.MarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketServiceTest {

    @Mock private MarketRepository marketRepository;
    @InjectMocks private MarketService marketService;

    private Market market;
    private List<Market> marketList;

    @BeforeEach
    public void setUp() {
        market = new Market();
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

    @Test
    public void test() {
        when(marketRepository.getStocks(any())).thenReturn(marketList);
        List<Market> expectMarketList = marketService.getStocks(any());

        assertEquals(marketList, expectMarketList);

        verify(marketRepository).getStocks(any());
        verify(marketRepository, times(1)).getStocks(any());
    }
}