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
            policyRespo.save(policyClaim);
        } else if(policyClaim.getClaimType().equals("CASHLESS") && policyClaim.getAmount().compareTo(amount)>500000){
            policyClaim.setClaimStatus(ClaimStatus.REJECTED);
            policyRespo.save(policyClaim);
        }
        if (policyClaim.getAmount().compareTo(amount)>0) {
            policyClaim.setClaimStatus(ClaimStatus.REJECTED);
            policyRespo.save(policyClaim);
        }else if(policyClaim.getAmount().compareTo(amount)>1000000){
            policyClaim.setClaimStatus(ClaimStatus.FRAUD);
            policyRespo.save(policyClaim);
        } 
        long diffInDays = policyClaim.getDateOfBirth().getDayOfYear() - policyClaim.getDateOfBirth().getDayOfYear();
        if(diffInDays>=90) {
            policyClaim.setClaimStatus(ClaimStatus.FRAUD);
            policyRespo.save(policyClaim);
        }
        else if (diffInDays <= 30) {
            policyClaim.setClaimStatus(ClaimStatus.REJECTED);
            policyRespo.save(policyClaim);
        } else {
            policyClaim.setClaimStatus(ClaimStatus.APPROVED);
            policyRespo.save(policyClaim);
        }

        Boolean existingClaim = policyRespo.existsById(policyClaim.getId());
        if (existingClaim) {
            policyClaim.setClaimStatus(ClaimStatus.FRAUD);
            policyRespo.save(policyClaim);
        }

    }
}
