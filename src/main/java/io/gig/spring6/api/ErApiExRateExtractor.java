package io.gig.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gig.spring6.exrate.ExRateData;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/06/10
 */
public class ErApiExRateExtractor implements ExRateExtractor {

    @Override
    public BigDecimal extract(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        BigDecimal exRate = data.rates().get("KRW");
        System.out.println("API ExRate: " + exRate);
        return exRate;
    }
}
