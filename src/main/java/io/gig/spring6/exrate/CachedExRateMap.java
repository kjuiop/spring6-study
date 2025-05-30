package io.gig.spring6.exrate;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author : JAKE
 * @date : 2025/05/31
 */
public class CachedExRateMap {

    private final BigDecimal value;
    private final Instant timestamp;


    public CachedExRateMap(BigDecimal value, Instant timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    // 현재시간으로부터 ttlMillis 만큼 경과되었는지 체크
    public boolean isExpired(long ttlMillis) {
        return Instant.now().toEpochMilli() - timestamp.toEpochMilli() > ttlMillis;
    }

    public BigDecimal getValue() {
        return this.value;
    }
}
