package com.claimsprocessingplatform.processingplatform.service;

import com.claimsprocessingplatform.processingplatform.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> findById(String id);
    boolean existsById(String id);
    void deleteById(String id);
}