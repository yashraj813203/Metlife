package com.claimsprocessingplatform.processingplatform.service;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;
import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponseDto;
import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.model.User;
import com.claimsprocessingplatform.processingplatform.repository.PolicyClaimRepository;
import com.claimsprocessingplatform.processingplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyClaimService {

    private final PolicyClaimRepository policyClaimRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PolicyClaimResponseDto createPolicy(PolicyClaimDto dto) {

        if (!policyClaimRepository.findAllByPolicyId(dto.getPolicyId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A policy with this ID already exists");
        }

        if (dto.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of birth cannot be in the future");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        PolicyClaim claim = modelMapper.map(dto, PolicyClaim.class);
        claim.setClaimStatus(ClaimStatus.PENDING);
        claim.setUser(user);

        PolicyClaim saved = policyClaimRepository.save(claim);
        return mapToResponse(saved);
    }

    public List<PolicyClaimResponseDto> getAllClaims() {
        List<PolicyClaim> claims = policyClaimRepository.findAll();
        if (claims.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No policy claims found");
        }
        return claims.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public PolicyClaimResponseDto getClaimById(String claimId) {
        PolicyClaim claim = policyClaimRepository.findById(claimId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found"));
        return mapToResponse(claim);
    }

    public Map<String, Double> getClaimAmountSummary() {
        var results = policyClaimRepository.sumClaimAmountsByStatus();
        Map<String, Double> summary = new HashMap<>();
        for (var r : results) {
            summary.put(r.getId(), r.getTotalAmount().doubleValue());
        }
        return summary;
    }

    private PolicyClaimResponseDto mapToResponse(PolicyClaim claim) {
        PolicyClaimResponseDto dto = modelMapper.map(claim, PolicyClaimResponseDto.class);
        if (claim.getUser() != null) {
            dto.setUserId(claim.getUser().getId());
            dto.setUserName(claim.getUser().getFullName());
            dto.setUserEmail(claim.getUser().getEmail());
        }
        return dto;
    }
}
