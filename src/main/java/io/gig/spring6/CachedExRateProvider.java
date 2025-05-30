package io.gig.spring6;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : JAKE
 * @date : 2025/05/31
 */
public class CachedExRateProvider implements ExRateProvider {

    private final ExRateProvider target;

    private final Map<String, CachedExRateMap> cache;
    private final long ttilMillis;

    public CachedExRateProvider(ExRateProvider target, long ttlMillis) {
        this.target = target;
        this.ttilMillis = ttlMillis;
        this.cache = new ConcurrentHashMap<>();
        // this.cache = new HashMap<>();
    }

    /**

    // ExRateProvider 를 받기 때문에 어떤 구현체에서 얻는 환율정보던 이 클래스를 통해 캐시를 할 수 있음
    // ConcurrentHashMap 을 사용해도 아래의 로직에서는 동시성 문제가 발생함
    @Override
    public BigDecimal getExRate(String currency) throws IOException {

        // 여기서 동시에 외부 API 를 호출할 때 레이턴시 발생으로 인해 cache 에 업데이트 하는 시점이 달라질 수 있기 때문
        // 1. cache data get
        // 2. 조건 확인
        // 3. 외부 호출 (시간 소요)
        // 4. cache update
        CachedExRateMap cached = this.cache.get(currency);
        if (cached == null || cached.isExpired(this.ttilMillis)) {
            BigDecimal freshValue = target.getExRate(currency);
            this.cache.put(currency, new CachedExRateMap(freshValue, Instant.now()));
            System.out.println("Cache Updated");
            return freshValue;
        }

        return cached.getValue();
    }

    **/

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        return cache.compute(currency, (key, existing) -> {
            if (existing == null || existing.isExpired(ttilMillis)) {
                try {
                    BigDecimal freshValue = target.getExRate(currency);
                    System.out.println("Cache Updated");
                    return new CachedExRateMap(freshValue, Instant.now());
                } catch (IOException e) {
                    throw new RuntimeException(e); // 예외 처리 주의
                }
            }
            return existing;
        }).getValue();
    }
}
