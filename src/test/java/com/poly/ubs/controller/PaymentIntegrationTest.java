package com.poly.ubs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.ubs.dto.SePayWebhookDTO;
import com.poly.ubs.entity.Bill;
import com.poly.ubs.service.BillServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillServiceImpl billService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSePayWebhook_Success_UnpaidToPaid() throws Exception {
        // 1. Mock Bill (Status: Chưa thanh toán)
        Bill mockBill = new Bill();
        mockBill.setId("HD123456");
        mockBill.setStatus("Chưa thanh toán");

        Mockito.when(billService.findAllBills()).thenReturn(Collections.singletonList(mockBill));

        // 2. Create Webhook Data
        SePayWebhookDTO webhookDTO = new SePayWebhookDTO();
        webhookDTO.setTransactionContent("Thanh toan don hang HD123456");
        webhookDTO.setTransferAmount(new BigDecimal("100000"));
        webhookDTO.setGateway("MBBank");

        // 3. Perform Request WITH AUTH HEADER (Apikey YOUR_SEPAY_API_TOKEN)
        mockMvc.perform(post("/api/payment/sepay-webhook")
                        .header("Authorization", "Apikey YOUR_SEPAY_API_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(webhookDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated bill HD123456"));

        // 4. Verify Service Call
        Mockito.verify(billService).updateStatus(eq("HD123456"), eq("Đã thanh toán"));
    }

    @Test
    public void testSePayWebhook_Unauthorized_MissingHeader() throws Exception {
        SePayWebhookDTO webhookDTO = new SePayWebhookDTO();
        webhookDTO.setTransactionContent("Thanh toan don hang HD123456");

        mockMvc.perform(post("/api/payment/sepay-webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(webhookDTO)))
                .andExpect(status().isUnauthorized()); // Expect 401
    }

    @Test
    public void testSePayWebhook_Unauthorized_WrongToken() throws Exception {
        SePayWebhookDTO webhookDTO = new SePayWebhookDTO();
        webhookDTO.setTransactionContent("Thanh toan don hang HD123456");

        mockMvc.perform(post("/api/payment/sepay-webhook")
                        .header("Authorization", "Apikey WRONG_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(webhookDTO)))
                .andExpect(status().isUnauthorized()); // Expect 401
    }
}