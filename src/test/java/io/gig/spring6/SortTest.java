package io.gig.spring6;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2025/06/03
 */
// JUnit 이 테스트 클래스에 대한 생성자를 생성한다.
// 이는 테스트 함수마다 클래스를 생성한다. 이는 테스트 별 독립적으로 수행되는 걸 보장해야하기 때문
public class SortTest {

    Sort sort;

    // 각 테스트 별 수행되기 전에 실행되는 함수
    // 테스트 갯수 만큼 수행된다.
    @BeforeEach
    void beforeEach() {
        sort = new Sort();
        // 각각 SortTest 라는 객체를 새로 만드는 것을 알 수 있음
        // Test1 : io.gig.spring6.SortTest@5db45159
        // Test2 : io.gig.spring6.SortTest@4567f35d
        // Test3 : io.gig.spring6.SortTest@6356695f
        System.out.println(this);
    }

    @Test
    void sort() {

        // 준비 (given)
        // Sort sort = new Sort();

        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // List.of 로 리스트 객체를 만들면 불변 리스트로 만들어지기 때문에 조심해야한다.
        // 실제 테스트를 돌리면 ImmutableCollections.uoe 의 에러가 발생한다.
        // List<String> list = sort.sortByLength(List.of("aa", "b"));

        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sort3Items() {

        // given
        // Sort sort = new Sort();

        // when
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        // then
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }

    @Test
    void alreadySorted() {

        // given
        // Sort sort = new Sort();

        // when
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        // then
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }
}
