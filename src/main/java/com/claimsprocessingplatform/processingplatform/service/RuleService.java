package com.claimsprocessingplatform.processingplatform.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.apache.tomcat.jni.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.repository.PolicyRespo;

@Service
public class RuleService {

    @Autowired
    private PolicyRespo policyRespo;

    public void allRuleService(PolicyClaim policyClaim) {
        BigDecimal amount = new BigDecimal(1000000);
        if (policyClaim.getClaimType().equals("CASH") && policyClaim.getAmount().compareTo(amount)>0) {
            policyClaim.setClaimStatus(ClaimStatus.REJECTED);
        } else if(policyClaim.getClaimType().equals("CASHLESS") && policyClaim.getAmount().compareTo(amount)>500000){
            policyClaim.setClaimStatus(ClaimStatus.REJECTED);
        }
        if (policyClaim.getAmount().compareTo(amount)>0) {
            policyClaim.setClaimStatus(ClaimStatus.REJECTED);
        }else if(policyClaim.getAmount().compareTo(amount)>1000000){
            policyClaim.setClaimStatus(ClaimStatus.FRAUD);
        } 
        
        
    }
    public String evaluateClaimEligibility(String policyId, String claimType, double amount) {
        if (amount > 1000000) {
            return "REJECTED";
        }else if(amount * 2>1000000){
            return "FRAUD";
        } 
        else if (claimType.equals("MEDICAL") && amount <= 10000) {
            return "APPROVED";
        } else {
            return "PENDING";
        }
    }
    public String dateEvaluation(LocalDate claimDate) {
        LocalDate currentDate = LocalDate.now();
        long diffInDays = currentDate.getDayOfYear() - claimDate.getDayOfYear();
        if (diffInDays <= 30) {
            return "ELIGIBLE";
        } else {
            return "INELIGIBLE";
        }
    }
    public String claimIdRepeat(String claimId) {
        Optional<PolicyClaim> existingClaim = policyRespo.findById(claimId);
        if (existingClaim.get().getId().equals(existingClaim)) {
            return "FRAUD";
        } else {
            return "UNIQUE";
        }
    }
}
