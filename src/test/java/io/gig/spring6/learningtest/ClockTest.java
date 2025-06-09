package io.gig.spring6.learningtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author : JAKE
 * @date : 2025/06/09
 */
public class ClockTest {
    // Clock 을 이용해서 LocalDateTime.now 가져오는 방법
    @Test
    void clock() {
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        Assertions.assertThat(dt2).isAfter(dt1);
    }

    // Clock 을 Test 에서 사용할 때 내가 원하는 시간을 지정해서 현재 시간을 가져오게 할 수 있는가
    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);
        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        Assertions.assertThat(dt2).isEqualTo(dt1);
        Assertions.assertThat(dt3).isEqualTo(dt1.plusHours(1));
    }
}
