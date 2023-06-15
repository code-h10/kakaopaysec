package com.kakaopaysec.stock.repository;

import com.kakaopaysec.stock.models.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRepository {

    List<Users> getAll();
    Users getById(int id);
    void insert(Users user);
    void deleteById(int id);

}
