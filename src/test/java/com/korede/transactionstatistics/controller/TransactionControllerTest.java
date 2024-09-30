package com.korede.transactionstatistics.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.korede.transactionstatistics.model.Transaction;
import com.korede.transactionstatistics.model.TransactionStatistics;
import com.korede.transactionstatistics.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class TransactionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testAddTransaction_HappyPath() throws Exception {
        String json = "{\"amount\": \"12.34\", \"timestamp\": \"" + Instant.now().toString() + "\"}";

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()); // Expect 201
    }

    @Test
    void testAddTransaction_OlderThan30Seconds() throws Exception {
        long timestamp = System.currentTimeMillis() - 31000; // 31 seconds ago
        String json = "{\"amount\": \"12.34\", \"timestamp\": \"" + Instant.ofEpochMilli(timestamp).toString() + "\"}";

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent()); // Expect 204
    }

//    @Test
//    void testAddTransaction_FutureTimestamp() throws Exception {
//        long futureTimestamp = System.currentTimeMillis() + 10000; // 10 seconds in the future
//        String json = "{\"amount\": \"12.34\", \"timestamp\": \"" + Instant.ofEpochMilli(futureTimestamp).toString() + "\"}";
//
//        mockMvc.perform(post("/transaction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isUnprocessableEntity()); // Expect 422
//    }

    @Test
    void testGetStatistics() throws Exception {
        TransactionStatistics stats = new TransactionStatistics();
        stats.addTransaction(new Transaction(new BigDecimal("12.34"), Instant.now().toEpochMilli()));

        when(transactionService.getStatistics()).thenReturn(stats);

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value("12.34"))
                .andExpect(jsonPath("$.average").value("12.34"))
                .andExpect(jsonPath("$.max").value("12.34"))
                .andExpect(jsonPath("$.min").value("12.34"))
                .andExpect(jsonPath("$.count").value(1));
    }
}