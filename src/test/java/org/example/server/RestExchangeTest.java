package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RestExchangeTest {

    private RestExchange sut;

    @Mock
    private HttpExchange exchange;

    @Before
    public void setUp() {
        sut = new RestExchange(exchange, null);
    }

    @Test
    public void returns_the_appropriate_method() {
        given(exchange.getRequestMethod()).willReturn("GET");

        HttpMethod result = sut.getMethod();

        assertThat(result).isEqualTo(HttpMethod.GET);
    }

    @Test
    public void reads_the_query_params_properly() throws URISyntaxException {
        given(exchange.getRequestURI()).willReturn(new URI("http://org.example/?aParam=a-value"));

        String result = sut.getParam("aParam");

        assertThat(result).isEqualTo("a-value");
    }

    @Test
    public void returns_null_when_the_param_is_not_found() throws URISyntaxException {
        given(exchange.getRequestURI()).willReturn(new URI("http://org.example/?aParam=a-value"));

        String result = sut.getParam("unknown");

        assertThat(result).isNull();
    }
}
