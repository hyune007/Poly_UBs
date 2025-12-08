package com.poly.ubs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Đối tượng chuyển giao dữ liệu (DTO) nhận thông tin Webhook từ cổng thanh toán SePay.
 * Dùng để ánh xạ dữ liệu JSON trả về khi có giao dịch phát sinh.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SePayWebhookDTO {
    /** Chuyển mã định danh duy nhất của giao dịch từ SePay. */
    private Long id;

    /** Chuyển tên cổng thanh toán (ví dụ: MBBank, VCB...). */
    private String gateway;
    
    /** Chuyển thời gian thực hiện giao dịch (được map từ field "transaction_date"). */
    @JsonProperty("transaction_date")
    private String transactionDate;
    
    /** Chuyển số tài khoản ngân hàng thụ hưởng (được map từ field "account_number"). */
    @JsonProperty("account_number")
    private String accountNumber;
    
    /** Chuyển thông tin tài khoản phụ (nếu có, map từ field "sub_account"). */
    @JsonProperty("sub_account")
    private String subAccount;
    
    /** Chuyển loại giao dịch (thu/chi, map từ field "transfer_type"). */
    @JsonProperty("transfer_type")
    private String transferType;
    
    /** Chuyển số tiền giao dịch (map từ field "transfer_amount"). */
    @JsonProperty("transfer_amount")
    private BigDecimal transferAmount;
    
    /** Chuyển số dư lũy kế sau giao dịch. */
    private BigDecimal accumulated;
    
    /** Chuyển mã tham chiếu giao dịch nội bộ của ngân hàng. */
    private String code;
    
    /** Chuyển nội dung chi tiết của giao dịch (map từ field "content"). */
    @JsonProperty("content")
    private String transactionContent;
    
    /** Chuyển mã tham chiếu mở rộng (map từ field "reference_code"). */
    @JsonProperty("reference_code")
    private String referenceCode;
    
    /** Chuyển nội dung toàn bộ body của thông báo (nếu có). */
    private String body;
}
