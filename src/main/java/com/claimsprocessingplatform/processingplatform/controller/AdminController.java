package com.claimsprocessingplatform.processingplatform.controller;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponseDto;
import com.claimsprocessingplatform.processingplatform.enums.ClaimStatus;
import com.claimsprocessingplatform.processingplatform.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Admin endpoints for managing policy claims")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get all claims")
    @GetMapping("/claims")
    public ResponseEntity<List<PolicyClaimResponseDto>> getAllClaims() {
        return ResponseEntity.ok(adminService.getAllClaims());
    }

    @Operation(summary = "Get claims by status")
    @GetMapping("/claims/status/{status}")
    public ResponseEntity<List<PolicyClaimResponseDto>> getClaimsByStatus(@PathVariable ClaimStatus status) {
        return ResponseEntity.ok(adminService.getClaimsByStatus(status));
    }

    @Operation(summary = "Update claim status (approve/reject)")
    @PatchMapping("/claims/{id}/status")
    public ResponseEntity<PolicyClaimResponseDto> updateClaimStatus(
            @PathVariable String id,
            @RequestParam ClaimStatus status) {
        return ResponseEntity.ok(adminService.updateClaimStatus(id, status));
    }

    @Operation(summary = "Get claim amount summary grouped by status")
    @GetMapping("/claims/summary")
    public ResponseEntity<Map<String, Double>> getClaimAmountSummary() {
        return ResponseEntity.ok(adminService.getClaimSummaryByStatus());
    }
}
