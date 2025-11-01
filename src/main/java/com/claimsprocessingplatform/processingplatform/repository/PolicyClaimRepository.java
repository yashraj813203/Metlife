package com.claimsprocessingplatform.processingplatform.repository;

import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PolicyClaimRepository extends MongoRepository<PolicyClaim, String> {

    List<PolicyClaim> findAllByPolicyId(String policyId);

    @Aggregation(pipeline = {
        "{ $group: { _id: '$claimStatus', totalAmount: { $sum: '$amount' } } }"
    })
    List<ClaimAmountSummary> sumClaimAmountsByStatus();

    interface ClaimAmountSummary {
        String getId();
        BigDecimal getTotalAmount();
    }
}
