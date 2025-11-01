package com.claimsprocessingplatform.processingplatform.controller;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponceDto;

import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.service.AzureBlobStorageService;
import com.claimsprocessingplatform.processingplatform.service.PolicyClaimService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/policies")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class PolicyController {

    @Autowired
    private PolicyClaimService policyService;
    @Autowired
    private AzureBlobStorageService azureBlobStorageService;

    public PolicyController(PolicyClaimService policyService,AzureBlobStorageService azureBlobStorageService) {
        this.policyService = policyService;
    }

    @PostMapping("/createPolicy")
    public ResponseEntity<String> createPolicy(@RequestBody PolicyClaimDto policyClaimDto) {
        this.policyService.createPolicy(policyClaimDto);
        return ResponseEntity.ok("Policy created successfully");
    }
    @GetMapping("/getAllClaims")
    public  ResponseEntity<List<PolicyClaimResponceDto>> getallClaims(){
    	 List<PolicyClaimResponceDto> policyClaimResponceDtos = this.policyService.getAllClaims();
         return ResponseEntity.ok(policyClaimResponceDtos);
    }
    

    @GetMapping("/getClaimsById/{id}")
    public  ResponseEntity<PolicyClaimResponceDto> getClaimsByid(@PathVariable String ClaimId ){
    	 PolicyClaimResponceDto policyClaimResponceDto = this.policyService.getClaimsByid(ClaimId)
                 .orElseThrow(() -> new IllegalArgumentException("Policy claim not found with ID: " + ClaimId));
         return ResponseEntity.ok(policyClaimResponceDto);
    }
    
    //Get total claim amounts grouped by status
    @GetMapping("/summary/status")
    public ResponseEntity<Map<String, Double>> getClaimAmountSummary() {
        return ResponseEntity.ok( policyService.getClaimAmountSummary());

    }

    @PostMapping("/upload")
    public String getMethodName(@RequestParam MultipartFile param) throws IOException {
        return ResponseEntity.ok(azureBlobStorageService.uploadFile(param,param.getOriginalFilename())).toString();
    }
    

}


