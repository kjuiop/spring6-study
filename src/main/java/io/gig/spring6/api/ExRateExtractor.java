package io.gig.spring6.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2025/06/10
 */
public interface ExRateExtractor {
    BigDecimal extract(String response) throws JsonProcessingException;
}
