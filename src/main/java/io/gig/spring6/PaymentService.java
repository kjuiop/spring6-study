package io.gig.spring6;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2025/05/24
 */
public class PaymentService {

    // java 는 HTTP 를 어떻게 다룰까? -> 블로그 주제?
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

        BigDecimal exRate = getKRWExRate(currency);

        // 위의 로직과 아래의 로직은 관심사가 다르다.
        // 위의 로직은 환율 정보를 가져오는 로직으로, 가져오는 API 에 대한 변경에 대한 요인으로 변경된다.
        // 그러나 아래의 로직은 우리 서비스 내에서 환율을 계산하는 로직이 변경될 때 변경된다.
        // 따라서 두 로직은 다른 시점에서 다른 이유로 변경되기 때문에 분리되는 것이 적합하다.

        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

    private BigDecimal getKRWExRate(String currency) throws IOException {

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

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println(payment);
    }
}
