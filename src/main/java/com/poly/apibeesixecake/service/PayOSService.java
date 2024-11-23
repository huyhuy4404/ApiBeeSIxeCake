package com.poly.apibeesixecake.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayOSService {

    private final RestTemplate restTemplate;

    public PayOSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateQRCode(String clientId, String apiKey, String checksumKey, String orderId, String amount) {
        String apiUrl = "https://sandbox-api.payos.vn/qrcode"; // Thay URL đúng với tài liệu PayOS

        // Header cho request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-Id", clientId);
        headers.set("Api-Key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body cho request
        Map<String, String> body = new HashMap<>();
        body.put("orderId", orderId);
        body.put("amount", amount);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // Gửi request tới PayOS
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody(); // JSON trả về chứa thông tin mã QR
            } else {
                throw new RuntimeException("Error response from PayOS: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling PayOS API: " + e.getMessage());
        }
    }
}
