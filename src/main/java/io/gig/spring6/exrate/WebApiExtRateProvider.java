package io.gig.spring6.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gig.spring6.api.ApiExecutor;
import io.gig.spring6.api.ErApiExRateExtractor;
import io.gig.spring6.api.ExRateExtractor;
import io.gig.spring6.api.SimpleApiExecutor;
import io.gig.spring6.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
// 추상화된 환율을 가져오는 클래스이다. 이 부분은 Client 에서 언제든 다른 클래스를 가져올 수 있다.
// @Component
public class WebApiExtRateProvider implements ExRateProvider {

    // IOException 은 네트워크 관련 오류인데, 이 오류는 이 클래스에서만 발생하지, 부모 클래스에서는 발생하지 않음
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
        return runAPIExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());
    }

    private BigDecimal runAPIExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = apiExecutor.execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exRateExtractor.extract(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
