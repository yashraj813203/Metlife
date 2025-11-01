package com.claimsprocessingplatform.processingplatform.model;

import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyClaim {

    @Id
    private String id;

    private String policyId;
    private String policyHolderName;
    private LocalDate dateOfBirth;

    private ClaimType claimType;
    private ClaimStatus claimStatus;

    @DBRef
    private User user;

    private BigDecimal amount;
    private String description;
}
