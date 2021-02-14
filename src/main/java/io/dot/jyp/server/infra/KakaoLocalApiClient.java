package io.dot.jyp.server.infra;

import io.dot.jyp.server.domain.MapClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoLocalApiClient implements MapClient {
    private RestTemplate restTemplate;

    public KakaoLocalApiClient(
            @Qualifier("kakaoLocalApiRestTemplate") RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
    }
}
