package com.kakaopaysec.stock.controller;

import com.kakaopaysec.stock.models.Users;
import com.kakaopaysec.stock.models.UsersWallet;
import com.kakaopaysec.stock.service.UserService;
import com.kakaopaysec.stock.service.UsersWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class UserController {

    private final UserService userService;
    private final UsersWalletService usersWalletService;

    /**
     * 가입 된 모든 사용자 조회
     * @author code-10
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAll() {
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    /**
     * 일치하는 ID 에 대한 사용자 조회
     * @author code-10
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getByEmail(@PathVariable int id) {
        return new ResponseEntity<>(userService.getById(id),HttpStatus.OK);
    }

    /**
     * 일치하는 ID 에 대한 현재 보유하고 있는 주식 정보 조회
     * @author code-10
     * @param id
     * @return
     */
    @GetMapping("/users/{id}/wallet")
    public ResponseEntity<List<UsersWallet>> getWalletByUserId(@PathVariable int id) {
        return new ResponseEntity<>(usersWalletService.getByUserId(id),HttpStatus.OK);
    }

    /**
     * 일치하는 ID 와 종목 코드에 대한 주식 정보 조회
     * @author code-10
     * @param id
     * @param stockCode
     * @return
     */
    @GetMapping("/users/{id}/{stockCode}/wallet")
    public ResponseEntity<UsersWallet> getWalletByUserIdAndStockCode(@PathVariable int id, @PathVariable String stockCode) {
        return new ResponseEntity<>(usersWalletService.getByUserIdAndStockCode(id, stockCode),HttpStatus.OK);
    }

    /**
     * 신규 사용자 등록
     * @author code-10
     * @param user
     */
    @PostMapping("/users")
    public ResponseEntity<Users> insert(@RequestBody Users user) {
        userService.insert(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    /**
     * 일치하는 ID 에 대한 사용자 삭제
     * @author code-10
     * @param id
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable int id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
