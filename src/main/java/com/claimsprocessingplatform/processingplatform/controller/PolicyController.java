package com.claimsprocessingplatform.processingplatform.controller;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;
import com.claimsprocessingplatform.processingplatform.service.PolicyClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/policies")
public class PolicyController {

    @Autowired
    private PolicyClaimService policyService;

    public PolicyController(PolicyClaimService policyService) {
        this.policyService = policyService;
    }

    @PostMapping("/createPolicy")
    public ResponseEntity<String> createPolicy(@RequestBody PolicyClaimDto policyClaimDto) {
        this.policyService.createPolicy(policyClaimDto);
        return ResponseEntity.ok("Policy created successfully");
    }

}


