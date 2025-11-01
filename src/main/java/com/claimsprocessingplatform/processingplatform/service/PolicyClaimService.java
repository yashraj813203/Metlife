package com.claimsprocessingplatform.processingplatform.service;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;
import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponceDto;
import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.repository.PolicyRespo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolicyClaimService {
    @Autowired
    private PolicyRespo policyRespo;

    public PolicyClaimService(PolicyRespo policyRespo){
        this.policyRespo = policyRespo;
    }
    public void createPolicy(PolicyClaimDto policyClaimDto) {

        if (policyClaimDto.getPolicyId() == null || policyClaimDto.getPolicyId().trim().isEmpty()) {
            throw new IllegalArgumentException("Policy ID is required");
        }

        if (policyClaimDto.getPolicyHolderName() == null || policyClaimDto.getPolicyHolderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Policy holder name is required");
        }

        if (policyClaimDto.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of birth is required");
        }

        if (policyClaimDto.getClaimType() == null) {
            throw new IllegalArgumentException("Claim type is required");
        }
        if (policyClaimDto.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in future");
        }
        if (policyRespo.findByPolicyId(policyClaimDto.getPolicyId()).isPresent()) {
            throw new IllegalArgumentException("A policy with this ID already exists");
        }
        PolicyClaim policyClaim = new PolicyClaim();
        policyClaim.setPolicyId(policyClaimDto.getPolicyId());
        policyClaim.setPolicyHolderName(policyClaimDto.getPolicyHolderName());
        policyClaim.setDateOfBirth(policyClaimDto.getDateOfBirth());
        policyClaim.setClaimType(policyClaimDto.getClaimType());
        policyClaim.setClaimStatus(ClaimStatus.PENDING);
        policyClaim.setUserId(policyClaimDto.getUserId());
        policyClaim.setAmount(policyClaimDto.getAmount());
        policyClaim.setDesc(policyClaimDto.getDesc());
        policyRespo.save(policyClaim);
        RuleService ruleService = new RuleService();
        ruleService.allRuleService(policyClaim);

    }



    public List<PolicyClaimResponceDto> getAllClaims() {
        if (policyRespo.findAll() == null || policyRespo.findAll().isEmpty()) {
            throw new IllegalArgumentException("No policy found");
        }
        List<PolicyClaim> policyClaims = policyRespo.findAll();
        return policyClaims.stream()
                .map(policyClaim -> new PolicyClaimResponceDto(
                        policyClaim.getId(),
                        policyClaim.getPolicyId(),
                        policyClaim.getPolicyHolderName(),
                        policyClaim.getDateOfBirth(),
                        policyClaim.getClaimType(),
                        policyClaim.getClaimStatus(),
                        policyClaim.getUserId(),
                        policyClaim.getAmount(),
                        policyClaim.getDesc()
                ))
                .collect(Collectors.toList());

    }

  
    public Optional<PolicyClaimResponceDto> getClaimsByid(String ClaimId) {
        Optional<PolicyClaim> policyClaimOptional = policyRespo.findById(ClaimId);
        return policyClaimOptional.map(policyClaim -> new PolicyClaimResponceDto(
                policyClaim.getId(),
                policyClaim.getPolicyId(),
                policyClaim.getPolicyHolderName(),
                policyClaim.getDateOfBirth(),
                policyClaim.getClaimType(),
                policyClaim.getClaimStatus(),
                policyClaim.getUserId(),
                policyClaim.getAmount(),
                policyClaim.getDesc()
        ));
    }
    
    public Map<String, Double> getClaimAmountSummary() {
        List<Map<String, Object>> results = policyRespo.sumClaimAmountsByStatus();

        Map<String, Double> summary = new HashMap<>();
        for (Map<String, Object> result : results) {
            String status = (String) result.get("_id");
            Double total = ((Number) result.get("totalAmount")).doubleValue();
            summary.put(status, total);
        }
        return summary;
    }

}