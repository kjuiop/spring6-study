package io.gig.spring6.exrate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : JAKE
 * @date : 2025/05/31
 */
public class CachedConcurrencyTest {

    public static void main(String[] args) throws InterruptedException {
        ExRateProvider slowProvider = new SlowExRateProvider();
        CachedExRateProvider cached = new CachedExRateProvider(slowProvider, 5000);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    BigDecimal rate = cached.getExRate("USD");
                    System.out.println(Thread.currentThread().getName() + " got rate: " + rate);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
    }

    // 가짜 환율 제공자 (느린 외부 API 흉내)
    static class SlowExRateProvider implements ExRateProvider {
        @Override
        public BigDecimal getExRate(String currency) throws IOException {
            try {
                Thread.sleep(1000); // 외부 API 응답 지연
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new BigDecimal("1234.56");
        }
    }
}
