package com.claimsprocessingplatform.processingplatform.dto;

import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyClaimDto {
    private String policyId;
    private String policyHolderName;
    private LocalDate dateOfBirth;
    private ClaimType claimType;
}
