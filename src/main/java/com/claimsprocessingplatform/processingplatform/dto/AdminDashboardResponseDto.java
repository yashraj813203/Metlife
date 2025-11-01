package com.claimsprocessingplatform.processingplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponseDto {

private long totalClaims;
private long claimsPassed;
private long claimsRejected;
private long claimsProcessing;

private double totalAmountClaimPassed;
private double totalAmountClaimFailed;
private double totalAmountClaimProcessing;
private double totalAmountClaimFraud;
private List<Map<String, Object>> highAmountClaims;
}