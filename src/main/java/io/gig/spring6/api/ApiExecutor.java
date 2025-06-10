package io.gig.spring6.api;

import java.io.IOException;
import java.net.URI;

/**
 * @author : JAKE
 * @date : 2025/06/10
 */
public interface ApiExecutor {
    String execute(URI uri) throws IOException;
}
