package com.claimsprocessingplatform.processingplatform.controller;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;
import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponseDto;
import com.claimsprocessingplatform.processingplatform.service.PolicyClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
@Tag(name = "Policy Controller", description = "Endpoints for creating and retrieving policy claims")
public class PolicyController {

    private final PolicyClaimService policyService;

    @Operation(summary = "Create a new policy claim")
    @ApiResponse(responseCode = "201", description = "Policy created successfully")
    @PostMapping
    public ResponseEntity<PolicyClaimResponseDto> createPolicy(@Valid @RequestBody PolicyClaimDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(policyService.createPolicy(dto));
    }

    @Operation(summary = "Get all policy claims")
    @GetMapping
    public ResponseEntity<List<PolicyClaimResponseDto>> getAllClaims() {
        return ResponseEntity.ok(policyService.getAllClaims());
    }

    @Operation(summary = "Get policy claim by ID")
    @ApiResponse(responseCode = "200", description = "Claim retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Claim not found")
    @GetMapping("/{id}")
    public ResponseEntity<PolicyClaimResponseDto> getClaimById(@PathVariable("id") String id) {
        return ResponseEntity.ok(policyService.getClaimById(id));
    }

    @Operation(summary = "Get claim amount summary grouped by status")
    @GetMapping("/summary/status")
    public ResponseEntity<Map<String, Double>> getClaimAmountSummary() {
        return ResponseEntity.ok(policyService.getClaimAmountSummary());
    }
}
