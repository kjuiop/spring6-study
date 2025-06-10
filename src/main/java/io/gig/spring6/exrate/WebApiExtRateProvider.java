package io.gig.spring6.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gig.spring6.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

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
        return runAPIExRate(url);
    }

    private BigDecimal runAPIExRate(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = executeAPI(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String executeAPI(URI uri) throws IOException {
        String response;
        // URLConnection 보다 HttpURLConnection 을 사용하면 HTTP 관련된 기능을 사용할 수 있음
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            response = br.lines().collect(Collectors.joining());
        }
        return response;
    }

    private BigDecimal extractExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        BigDecimal exRate = data.rates().get("KRW");
        System.out.println("API ExRate: " + exRate);
        return exRate;
    }
}
