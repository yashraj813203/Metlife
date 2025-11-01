package com.claimsprocessingplatform.processingplatform.dto;

import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyClaimResponseDto {

    private String id;
    private String policyId;
    private String policyHolderName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private ClaimType claimType;
    private ClaimStatus claimStatus;

    private BigDecimal amount;
    private String description;

    private String userId;
    private String userName;
    private String userEmail;
}
