package com.claimsprocessingplatform.processingplatform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.claimsprocessingplatform.processingplatform.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {


    Optional<User> findByEmail(String email);
}