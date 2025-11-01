package com.claimsprocessingplatform.processingplatform.repository;

import com.claimsprocessingplatform.processingplatform.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Case-insensitive email search
    @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
