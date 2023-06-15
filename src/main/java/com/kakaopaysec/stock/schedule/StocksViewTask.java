package com.kakaopaysec.stock.schedule;

import com.kakaopaysec.stock.models.StocksView;
import com.kakaopaysec.stock.service.StocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class StocksViewTask {

    private final StocksService stocksService;
    private final RedisTemplate<String, Long> redisTemplate;

    /**
     * - 레디스에 저장된 주식 상세 조회수를 5분간격으로 데이터베이스에 저장 후, 조회수 0 으로 초기화
     * - 다중 서버에서 해당 스케줄러가 중복 실행 되지 않도록 @SchedulerLock 설정
     * @author code-10
     */

//    @Scheduled(cron = "*/1 * * * * *")
    @Scheduled(cron = "0 0/5 * * * 1-5")
    @SchedulerLock(name = "UpdateStockViewCountTask", lockAtLeastFor = "PT30S", lockAtMostFor = "PT30S")
    public void updateStocksViewCount() {

        RedisOperations<String, Long> operations =  redisTemplate.opsForValue().getOperations();
        Set<String> keys = operations.keys("*");

        log.info("===== Real Time Views Count Key ===== {}", operations.opsForValue());

        if (keys != null && !keys.isEmpty()) {

            StocksView stocksView = null;
            List<StocksView> stocksViewList = new ArrayList<>();

            for (String key : keys) {

                Long count = operations.opsForValue().get(key);
                if (count > 0) {
                    stocksView = new StocksView();
                    stocksView.setStockCode(key);
                    stocksView.setViewsCount(count);
                    stocksViewList.add(stocksView);

                    operations.delete(key); // 레디스에 저장된 조회수 초기화
                }
            }
            log.info("===== Current Views Count ===== {}", stocksViewList);
            stocksService.updateViewsCountByStockCode(stocksViewList);
        }
    }
}
