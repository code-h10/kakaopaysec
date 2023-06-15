package com.kakaopaysec.stock.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    private int id;
    private String name;
    private String email;
    private UsersWallet usersWallet;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}
