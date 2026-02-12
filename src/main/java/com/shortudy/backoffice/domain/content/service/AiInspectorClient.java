package com.shortudy.backoffice.domain.content.service;

import com.shortudy.backoffice.domain.content.dto.response.AiInspectorResponse;
import com.shortudy.backoffice.global.error.BaseException;
import com.shortudy.backoffice.global.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 외부 AI Agent 연동 클라이언트
 */
@Component
public class AiInspectorClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.ai-agent.base-url:http://localhost:8000}")
    private String aiAgentBaseUrl;

    public AiInspectorResponse inspect(Long shortsId) {
        String url = aiAgentBaseUrl + "/api/v1/inspect/" + shortsId;

        try {
            ResponseEntity<AiInspectorResponse> response = restTemplate.postForEntity(url, null, AiInspectorResponse.class);
            return response.getBody();
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR, "AI 검수 연동에 실패했습니다.");
        }
    }
}
