package com.poly.ubs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SePayWebhookDTO {
    private Long id;
    private String gateway;
    
    @JsonProperty("transaction_date")
    private String transactionDate;
    
    @JsonProperty("account_number")
    private String accountNumber;
    
    @JsonProperty("sub_account")
    private String subAccount;
    
    @JsonProperty("transfer_type")
    private String transferType;
    
    @JsonProperty("transfer_amount")
    private BigDecimal transferAmount;
    
    private BigDecimal accumulated;
    
    private String code;
    
    @JsonProperty("content")
    private String transactionContent;
    
    @JsonProperty("reference_code")
    private String referenceCode;
    
    private String body;
}
