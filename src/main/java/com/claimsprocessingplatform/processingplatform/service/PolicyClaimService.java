package com.claimsprocessingplatform.processingplatform.service;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;
import com.claimsprocessingplatform.processingplatform.enums.ClimStatus;
import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.repository.PolicyRespo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        if (policyRespo.findByPolicyId(policyClaimDto.getPolicyId()).isPresent()) {
            throw new IllegalArgumentException("A policy with this ID already exists");
        }
        PolicyClaim policyClaim = new PolicyClaim();
        policyClaim.setPolicyId(policyClaimDto.getPolicyId());
        policyClaim.setPolicyHolderName(policyClaimDto.getPolicyHolderName());
        policyClaim.setDateOfBirth(policyClaimDto.getDateOfBirth());
        policyClaim.setClaimType(policyClaimDto.getClaimType());
        policyClaim.setClimStatus(ClimStatus.PENDING);

        policyRespo.save(policyClaim);
    }

}