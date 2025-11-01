package com.claimsprocessingplatform.processingplatform.service;

import java.util.List;
import com.claimsprocessingplatform.processingplatform.repository.*;
import com.claimsprocessingplatform.processingplatform.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminService {
    @Autowired
    private PolicyRespo policyRespo;

    public List<PolicyClaim> getAllClaims() {
        List<PolicyClaim> claims = policyRespo.findAll();
        return claims;
    }
}
