package com.claimsprocessingplatform.processingplatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.claimsprocessingplatform.processingplatform.service.*;
import com.claimsprocessingplatform.processingplatform.model.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @GetMapping("/get-all-claims")
    public List<PolicyClaim> getMethodName() {
        return this.adminService.getAllClaims();
    }
    
}
