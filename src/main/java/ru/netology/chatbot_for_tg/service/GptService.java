package ru.netology.chatbot_for_tg.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@Service
public class GptService {

    @Value("${gemini.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;
    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GptService() {
        this.restTemplate = new RestTemplate();
    }

    public String getGptResponse(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> part = new HashMap<>();
            part.put("text", prompt);

            Map<String, Object> content = new HashMap<>();
            content.put("parts", Collections.singletonList(part));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", Collections.singletonList(content));

            String url = GEMINI_API_URL + "?key=" + apiKey;
            
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Map.class
            );

            Map responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> content1 = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content1.get("parts");
                    return (String) parts.get(0).get("text");
                }
            }
            return "Извините, не удалось сгенерировать ответ.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Извините, произошла ошибка при обработке вашего запроса: " + e.getMessage();
        }
    }
}
