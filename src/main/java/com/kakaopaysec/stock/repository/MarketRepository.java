package com.kakaopaysec.stock.repository;

import com.kakaopaysec.stock.models.Market;
import com.kakaopaysec.stock.utils.Pageable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MarketRepository {

    List<Market> getStocks(Pageable pageable);
}
