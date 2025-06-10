package io.gig.spring6.exrate;

import io.gig.spring6.api.APITemplate;
import io.gig.spring6.api.ErApiExRateExtractor;
import io.gig.spring6.api.HttpClientApiExtractor;
import io.gig.spring6.payment.ExRateProvider;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
// 추상화된 환율을 가져오는 클래스이다. 이 부분은 Client 에서 언제든 다른 클래스를 가져올 수 있다.
// @Component
public class WebApiExtRateProvider implements ExRateProvider {

    private final APITemplate apiTemplate = new APITemplate();

    // IOException 은 네트워크 관련 오류인데, 이 오류는 이 클래스에서만 발생하지, 부모 클래스에서는 발생하지 않음
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
        return this.apiTemplate.getExRate(url, new HttpClientApiExtractor(), new ErApiExRateExtractor());
    }
}
