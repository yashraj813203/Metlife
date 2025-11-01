package com.claimsprocessingplatform.processingplatform.service;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponseDto;
import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.repository.PolicyClaimRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PolicyClaimRepository policyClaimRepository;
    private final ModelMapper modelMapper;

    public List<PolicyClaimResponseDto> getAllClaims() {
        List<PolicyClaim> claims = policyClaimRepository.findAll();
        if (claims.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No claims found");
        }
        return claims.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<PolicyClaimResponseDto> getClaimsByStatus(ClaimStatus status) {
        List<PolicyClaim> claims = policyClaimRepository.findAll().stream()
                .filter(c -> c.getClaimStatus() == status)
                .collect(Collectors.toList());
        return claims.stream().map(this::toDto).collect(Collectors.toList());
    }

    public PolicyClaimResponseDto updateClaimStatus(String claimId, ClaimStatus status) {
        PolicyClaim claim = policyClaimRepository.findById(claimId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found"));
        claim.setClaimStatus(status);
        return toDto(policyClaimRepository.save(claim));
    }

    public Map<String, Double> getClaimSummaryByStatus() {
        return policyClaimRepository.sumClaimAmountsByStatus().stream()
                .collect(Collectors.toMap(
                        PolicyClaimRepository.ClaimAmountSummary::getId,
                        s -> s.getTotalAmount().doubleValue()
                ));
    }

    private PolicyClaimResponseDto toDto(PolicyClaim claim) {
        PolicyClaimResponseDto dto = modelMapper.map(claim, PolicyClaimResponseDto.class);
        if (claim.getUser() != null) {
            dto.setUserId(claim.getUser().getId());
            dto.setUserName(claim.getUser().getFullName());
            dto.setUserEmail(claim.getUser().getEmail());
        }
        return dto;
    }
}
