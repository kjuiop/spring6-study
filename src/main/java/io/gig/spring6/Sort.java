package io.gig.spring6;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2025/05/28
 */
public class Sort {

    public List<String> sortByLength(List<String> list) {
        list.sort((o1, o2) -> o1.length() - o2.length());
        return list;
    }

}
