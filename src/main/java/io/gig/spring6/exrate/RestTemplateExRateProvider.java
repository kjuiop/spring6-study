package io.gig.spring6.exrate;

import io.gig.spring6.payment.ExRateProvider;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/06/10
 */
public class RestTemplateExRateProvider implements ExRateProvider {

    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
        return this.restTemplate.getForObject(url, ExRateData.class).rates().get("KRW");
    }
}
