package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.server.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RestServerTest {

    private RestServer sut;

    @Mock
    private HttpServer server;

    @Mock
    private Function<RestExchange, Object> spyHandler;

    @Before
    public void serUp() {
        sut = new RestServer(server);
    }

    @Test
    public void should_map_the_handler_correctly() {
        sut.nest("/test-context", RestServer.get(spyHandler));

        assertThat(sut.handlers.get("/test-context").get(HttpMethod.GET)).isEqualTo(spyHandler);
    }

    @Test
    public void get_should_pair_the_right_method() {
        Pair<HttpMethod, Function<RestExchange, Object>> response = RestServer.get(spyHandler);

        assertThat(response.getFirst()).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void post_should_pair_the_right_method() {
        Pair<HttpMethod, Function<RestExchange, Object>> response = RestServer.post(spyHandler);

        assertThat(response.getFirst()).isEqualTo(HttpMethod.POST);
    }

    @Test
    public void delete_should_pair_the_right_method() {
        Pair<HttpMethod, Function<RestExchange, Object>> response = RestServer.delete(spyHandler);

        assertThat(response.getFirst()).isEqualTo(HttpMethod.DELETE);
    }
}
