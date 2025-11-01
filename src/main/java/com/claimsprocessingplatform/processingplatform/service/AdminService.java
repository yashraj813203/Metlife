package com.claimsprocessingplatform.processingplatform.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.claimsprocessingplatform.processingplatform.repository.*;
import com.claimsprocessingplatform.processingplatform.dto.AdminDashboardResponseDto;
import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private PolicyRespo policyClaimRepository;

    public List<PolicyClaim> getAllClaims() {
        List<PolicyClaim> claims = policyClaimRepository.findAll();
        return claims;
    }


    /** Build the Admin Dashboard summary */
    public AdminDashboardResponseDto getAdminDashboard() {
        List<PolicyClaim> claims = policyClaimRepository.findAll();

        if (claims.isEmpty()) {
            return new AdminDashboardResponseDto(0, 0, 0, 0, 0, 0, 0,0, Collections.emptyList());
        }

        // count by status
        long totalClaims = claims.size();

        long claimsPassed = claims.stream().filter(c -> c.getClaimStatus() == ClaimStatus.APPROVED).count();
        long claimsRejected = claims.stream().filter(c -> c.getClaimStatus() == ClaimStatus.REJECTED).count();
        long claimsProcessing = claims.stream().filter(c -> c.getClaimStatus() == ClaimStatus.PENDING).count();

        // sum by status
        double totalAmountClaimPassed = claims.stream()
                .filter(c -> c.getClaimStatus() == ClaimStatus.APPROVED)
                .mapToDouble(c -> c.getAmount().doubleValue())
                .sum();

        double totalAmountClaimFailed = claims.stream()
                .filter(c -> c.getClaimStatus() == ClaimStatus.REJECTED)
                .mapToDouble(c -> c.getAmount().doubleValue())
                .sum();

        double totalAmountClaimProcessing = claims.stream()
                .filter(c -> c.getClaimStatus() == ClaimStatus.PENDING)
                .mapToDouble(c -> c.getAmount().doubleValue())
                .sum();

        double totalAmountClaimFraud = claims.stream()
                .filter(c -> c.getClaimStatus() == ClaimStatus.FRAUD)
                .mapToDouble(c -> c.getAmount().doubleValue())
                .sum();
  
        List<Map<String, Object>> highAmountClaims = claims.stream()

                .sorted(Comparator.comparing(PolicyClaim::getAmount, Comparator.nullsLast(BigDecimal::compareTo)).reversed())

                .limit(5)

                .map(c -> {

                    Map<String, Object> map = new HashMap<>();

                    map.put("policyId", c.getPolicyId());

                    map.put("policyHolderName", c.getPolicyHolderName());

                    map.put("amount", c.getAmount());

                    map.put("claimStatus", c.getClaimStatus().name());

                    return map;

                })

                .collect(Collectors.toList());

        return AdminDashboardResponseDto.builder()
                .totalClaims(totalClaims)
                .claimsPassed(claimsPassed)
                .claimsRejected(claimsRejected)
                .claimsProcessing(claimsProcessing)
                .totalAmountClaimPassed(totalAmountClaimPassed)
                .totalAmountClaimFailed(totalAmountClaimFailed)
                .totalAmountClaimProcessing(totalAmountClaimProcessing)
                .totalAmountClaimFraud(totalAmountClaimFraud)
                .highAmountClaims(highAmountClaims)
                .build();
    }
}
