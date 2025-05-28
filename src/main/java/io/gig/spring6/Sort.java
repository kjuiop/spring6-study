package io.gig.spring6;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2025/05/28
 */
public class Sort {

    public static void main(String[] args) {
        List<String> scores = Arrays.asList("z", "x", "spring", "java");
        // sort 함수는 2번째 파라미터로 넘긴 전략 오브젝트를 받아서 알고리즘을 적용한다.
        Collections.sort(scores, (o1, o2) -> o1.length() - o2.length());
        scores.forEach(System.out::println);
    }

}
