package com.claimsprocessingplatform.processingplatform.service;

import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimDto;
import com.claimsprocessingplatform.processingplatform.dto.PolicyClaimResponceDto;
import com.claimsprocessingplatform.processingplatform.enums.ClaimType;
import com.claimsprocessingplatform.processingplatform.enums.ClimStatus;
import com.claimsprocessingplatform.processingplatform.model.PolicyClaim;
import com.claimsprocessingplatform.processingplatform.model.User;
import com.claimsprocessingplatform.processingplatform.repository.PolicyRespo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyClaimServiceTest {

    @Mock
    private PolicyRespo policyRespo;

    @InjectMocks
    private PolicyClaimService policyClaimService;

    private PolicyClaimDto validPolicyClaimDto;
    private PolicyClaim samplePolicyClaim;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User("1", "john_doe", "john@example.com", "John Doe");

        validPolicyClaimDto = new PolicyClaimDto();
        validPolicyClaimDto.setPolicyId("POL123");
        validPolicyClaimDto.setPolicyHolderName("John Doe");
        validPolicyClaimDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        validPolicyClaimDto.setClaimType(ClaimType.CASH);
        validPolicyClaimDto.setUserId(sampleUser);
        validPolicyClaimDto.setAmount(new BigDecimal("5000.00"));
        validPolicyClaimDto.setDesc("Medical claim for surgery");

        samplePolicyClaim = new PolicyClaim();
        samplePolicyClaim.setId("1");
        samplePolicyClaim.setPolicyId("POL123");
        samplePolicyClaim.setPolicyHolderName("John Doe");
        samplePolicyClaim.setDateOfBirth(LocalDate.of(1990, 1, 1));
        samplePolicyClaim.setClaimType(ClaimType.CASH);
        samplePolicyClaim.setClimStatus(ClimStatus.PENDING);
        samplePolicyClaim.setUserId(sampleUser);
        samplePolicyClaim.setAmount(new BigDecimal("5000.00"));
        samplePolicyClaim.setDesc("Medical claim for surgery");
    }

    // ========== createPolicy Method Tests ==========

    @Test
    void createPolicy_ValidPolicyClaimDto_ShouldSavePolicyClaim() {
        // Given
        when(policyRespo.findByPolicyId("POL123")).thenReturn(Optional.empty());

        // When
        policyClaimService.createPolicy(validPolicyClaimDto);

        // Then
        verify(policyRespo, times(1)).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_NullPolicyId_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setPolicyId(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("Policy ID is required", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_EmptyPolicyId_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setPolicyId("");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("Policy ID is required", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_NullPolicyHolderName_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setPolicyHolderName(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("Policy holder name is required", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_EmptyPolicyHolderName_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setPolicyHolderName("   ");

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("Policy holder name is required", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_NullDateOfBirth_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setDateOfBirth(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("Date of birth is required", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_FutureDateOfBirth_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setDateOfBirth(LocalDate.now().plusDays(1));

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals(exception.getMessage(), "Date of birth cannot be in future");
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_NullClaimType_ShouldThrowIllegalArgumentException() {
        // Given
        validPolicyClaimDto.setClaimType(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("Claim type is required", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    @Test
    void createPolicy_DuplicatePolicyId_ShouldThrowIllegalArgumentException() {
        // Given
        when(policyRespo.findByPolicyId("POL123")).thenReturn(Optional.of(samplePolicyClaim));

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.createPolicy(validPolicyClaimDto)
        );
        assertEquals("A policy with this ID already exists", exception.getMessage());
        verify(policyRespo, never()).save(any(PolicyClaim.class));
    }

    // ========== getAllClaims Method Tests ==========

    @Test
    void getAllClaims_ExistingClaims_ShouldReturnListOfPolicyClaimResponceDto() {
        // Given
        List<PolicyClaim> policyClaims = Arrays.asList(samplePolicyClaim);
        when(policyRespo.findAll()).thenReturn(policyClaims);

        // When
        List<PolicyClaimResponceDto> result = policyClaimService.getAllClaims();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        PolicyClaimResponceDto responseDto = result.get(0);
        assertEquals(samplePolicyClaim.getId(), responseDto.getId());
        assertEquals(samplePolicyClaim.getPolicyId(), responseDto.getPolicyId());
        assertEquals(samplePolicyClaim.getPolicyHolderName(), responseDto.getPolicyHolderName());
        assertEquals(samplePolicyClaim.getDateOfBirth(), responseDto.getDateOfBirth());
        assertEquals(samplePolicyClaim.getClaimType(), responseDto.getClaimType());
        assertEquals(samplePolicyClaim.getClimStatus(), responseDto.getClimStatus());
        assertEquals(samplePolicyClaim.getUserId(), responseDto.getUserId());
        assertEquals(samplePolicyClaim.getAmount(), responseDto.getAmount());
        assertEquals(samplePolicyClaim.getDesc(), responseDto.getDesc());
    }

    @Test
    void getAllClaims_MultipleClaims_ShouldReturnListOfAllPolicyClaimResponceDto() {
        // Given
        PolicyClaim secondPolicyClaim = new PolicyClaim();
        secondPolicyClaim.setId("2");
        secondPolicyClaim.setPolicyId("POL456");
        secondPolicyClaim.setPolicyHolderName("Jane Smith");
        secondPolicyClaim.setDateOfBirth(LocalDate.of(1985, 5, 15));
        secondPolicyClaim.setClaimType(ClaimType.CASHLESS);
        secondPolicyClaim.setClimStatus(ClimStatus.APPROVED);
        secondPolicyClaim.setUserId(sampleUser);
        secondPolicyClaim.setAmount(new BigDecimal("3000.00"));
        secondPolicyClaim.setDesc("Dental claim");

        List<PolicyClaim> policyClaims = Arrays.asList(samplePolicyClaim, secondPolicyClaim);
        when(policyRespo.findAll()).thenReturn(policyClaims);

        // When
        List<PolicyClaimResponceDto> result = policyClaimService.getAllClaims();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("POL123", result.get(0).getPolicyId());
        assertEquals("POL456", result.get(1).getPolicyId());
    }

    @Test
    void getAllClaims_EmptyList_ShouldThrowIllegalArgumentException() {
        // Given
        when(policyRespo.findAll()).thenReturn(Arrays.asList());

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.getAllClaims()
        );
        assertEquals("No policy found", exception.getMessage());
    }

    @Test
    void getAllClaims_NullList_ShouldThrowIllegalArgumentException() {
        // Given
        when(policyRespo.findAll()).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> policyClaimService.getAllClaims()
        );
        assertEquals("No policy found", exception.getMessage());
    }

    // ========== getClaimsByid Method Tests ==========

    @Test
    void getClaimsByid_ExistingClaimId_ShouldReturnPolicyClaimResponceDto() {
        // Given
        when(policyRespo.findById("1")).thenReturn(Optional.of(samplePolicyClaim));

        // When
        Optional<PolicyClaimResponceDto> result = policyClaimService.getClaimsByid("1");

        // Then
        assertTrue(result.isPresent());
        PolicyClaimResponceDto responseDto = result.get();
        assertEquals(samplePolicyClaim.getId(), responseDto.getId());
        assertEquals(samplePolicyClaim.getPolicyId(), responseDto.getPolicyId());
        assertEquals(samplePolicyClaim.getPolicyHolderName(), responseDto.getPolicyHolderName());
        assertEquals(samplePolicyClaim.getDateOfBirth(), responseDto.getDateOfBirth());
        assertEquals(samplePolicyClaim.getClaimType(), responseDto.getClaimType());
        assertEquals(samplePolicyClaim.getClimStatus(), responseDto.getClimStatus());
        assertEquals(samplePolicyClaim.getUserId(), responseDto.getUserId());
        assertEquals(samplePolicyClaim.getAmount(), responseDto.getAmount());
        assertEquals(samplePolicyClaim.getDesc(), responseDto.getDesc());
    }

    @Test
    void getClaimsByid_NonExistingClaimId_ShouldReturnEmptyOptional() {
        // Given
        when(policyRespo.findById("999")).thenReturn(Optional.empty());

        // When
        Optional<PolicyClaimResponceDto> result = policyClaimService.getClaimsByid("999");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void getClaimsByid_NullClaimId_ShouldReturnEmptyOptional() {
        // Given
        when(policyRespo.findById(null)).thenReturn(Optional.empty());

        // When
        Optional<PolicyClaimResponceDto> result = policyClaimService.getClaimsByid(null);

        // Then
        assertFalse(result.isPresent());
    }

    // ========== Integration-style Tests ==========

    @Test
    void createPolicy_ValidPolicyClaimDto_ShouldSetPendingStatus() {
        // Given
        when(policyRespo.findByPolicyId("POL123")).thenReturn(Optional.empty());

        // When
        policyClaimService.createPolicy(validPolicyClaimDto);

        // Then
        verify(policyRespo, times(1)).save(argThat(policyClaim ->
            policyClaim.getClimStatus() == ClimStatus.PENDING
        ));
    }

    @Test
    void createPolicy_ValidPolicyClaimDto_ShouldMapAllFieldsCorrectly() {
        // Given
        when(policyRespo.findByPolicyId("POL123")).thenReturn(Optional.empty());

        // When
        policyClaimService.createPolicy(validPolicyClaimDto);

        // Then
        verify(policyRespo, times(1)).save(argThat(policyClaim ->
            policyClaim.getPolicyId().equals("POL123") &&
            policyClaim.getPolicyHolderName().equals("John Doe") &&
            policyClaim.getDateOfBirth().equals(LocalDate.of(1990, 1, 1)) &&
            policyClaim.getClaimType() == ClaimType.CASH &&
            policyClaim.getUserId().equals(sampleUser) &&
            policyClaim.getAmount().equals(new BigDecimal("5000.00")) &&
            policyClaim.getDesc().equals("Medical claim for surgery")
        ));
    }
}