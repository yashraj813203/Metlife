package com.claimsprocessingplatform.processingplatform.repository;

import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRespo extends MongoRepository<PolicyClaim, String> {
    Optional<PolicyClaim> findByPolicyId(String policyId);
    
    
}
