package com.claimsprocessingplatform.processingplatform.repository;

import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PolicyRespo extends MongoRepository<PolicyClaim, String> {
    Optional<PolicyClaim> findByPolicyId(String policyId);
    
    // Group by claim status and calculate total amount for each
    @Aggregation(pipeline = {
        "{ $group: { _id: '$claimStatus', totalAmount: { $sum: '$amount' } } }"
    })
    List<Map<String, Object>> sumClaimAmountsByStatus();
}
