package io.gig.spring6;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2025/05/25
 */
// 추상화된 환율을 가져오는 클래스이다. 이 부분은 Client 에서 언제든 다른 클래스를 가져올 수 있다.
@Component
public class WebApiExtRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        // URLConnection 보다 HttpURLConnection 을 사용하면 HTTP 관련된 기능을 사용할 수 있음
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        BigDecimal exRate = data.rates().get("KRW");
        System.out.println(exRate);
        return exRate;
    }
}
