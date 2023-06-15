package com.kakaopaysec.stock.service;

import com.kakaopaysec.stock.models.Users;
import com.kakaopaysec.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Users> getAll() {
        return userRepository.getAll();
    }

    public Users getById(int id) { return userRepository.getById(id); }

    public void insert(Users user) {
        userRepository.insert(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
