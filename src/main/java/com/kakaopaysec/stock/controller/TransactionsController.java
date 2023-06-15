package com.kakaopaysec.stock.controller;

import com.kakaopaysec.stock.models.Transactions;
import com.kakaopaysec.stock.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/test")
public class TransactionsController {

    private final TransactionService transactionService;


    /**
     * 신규 체결 건 등록 시 해당 종목의 현재 가격 변동
     * @author code-10
     * @param transaction
     */
    @PostMapping("/transactions")
    public ResponseEntity<Transactions> insert(@RequestBody Transactions transaction) {
        transactionService.insert(transaction);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    /**
     * 체결된 모든 거래 건 조회
     * @author code-10
     * @return
     * @throws Exception
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<Transactions>> getAll() throws Exception {
        return new ResponseEntity<>(transactionService.getAll(),HttpStatus.OK);
    }



    /**
     * 일치하는 ID 에 대한 거래 건 조회
     * @author code-10
     * @param id
     * @return
     */
    @GetMapping("/transactions/id/{id}")
    public ResponseEntity<Transactions> getById(@PathVariable int id) {
        return new ResponseEntity<>(transactionService.getById(id),HttpStatus.OK);
    }

    /**
     * 일치하는 종목 코드에 대한 모든 거래 건 조회
     * @author code-10
     * @param stockCode
     * @return
     */
    @GetMapping("/transactions/stock-code/{stockCode}")
    public ResponseEntity<List<Transactions>> getByStockCode(@PathVariable String stockCode) {
        return new ResponseEntity<>(transactionService.getByStockCode(stockCode),HttpStatus.OK);
    }

    /**
     * 일치하는 매수 / 매도별 타입에 대한 거래 건 조회
     * @author code-10
     * @param type
     * @return
     */
    @GetMapping("/transactions/type/{type}")
    public ResponseEntity<List<Transactions>> getByType(@PathVariable int type) {
        return new ResponseEntity<>(transactionService.getByType(type),HttpStatus.OK);
    }


}
