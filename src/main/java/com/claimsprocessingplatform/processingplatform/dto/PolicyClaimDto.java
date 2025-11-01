package com.claimsprocessingplatform.processingplatform.dto;


import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyClaimDto {

    @NotBlank(message = "Policy ID is required")
    private String policyId;

    @NotBlank(message = "Policy holder name is required")
    private String policyHolderName;

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Claim type is required")
    private ClaimType claimType;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Claim amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    private String description;

    // optional at creation; default to PENDING
    private ClaimStatus claimStatus = ClaimStatus.PENDING;
}
