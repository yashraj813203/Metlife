package com.claimsprocessingplatform.processingplatform.model;

import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import com.claimsprocessingplatform.processingplatform.enums.ClimStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "policies")
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
    private ClimStatus climStatus;


    @DBRef(lazy = true)
    private User userId;

    private BigDecimal amount;
    private String desc;
}
