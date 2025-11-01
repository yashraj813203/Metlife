package com.claimsprocessingplatform.processingplatform.dto;

import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import com.claimsprocessingplatform.processingplatform.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyClaimDto {
    private String policyId;
    private String policyHolderName;
    private LocalDate dateOfBirth;
    private ClaimType claimType;
    private User userId;
    private BigDecimal amount;
    private String desc;
}
