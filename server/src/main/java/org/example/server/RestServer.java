package org.example.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.example.logger.LoggerFactory;
import org.example.server.util.Pair;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RestServer {

    private static Logger logger = LoggerFactory.getLogger(RestServer.class);

    private HttpServer server;
    private ObjectMapper mapper;
    private int port;
    Map<String, Map<HttpMethod, Function<RestExchange, Object>>> handlers;

    /**
     * Creates a new RestServer. Defaults to the port 8080
     */
    public RestServer() throws IOException {
        this(8080);
    }

    /**
     * Creates a new RestServer on the given port
     *
     * @param port the port to use
     */
    public RestServer(int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.init();
    }

    /**
     * Constructor that allows injection of the HttpServer
     */
    RestServer(HttpServer server) {
        this.server = server;
        this.init();
    }

    /**
     * Initialize all contexts and handlers. Then, start up the server.
     */
    public void start() {
        handlers.forEach((context, handlers) -> {
            HttpContext httpContext = this.server.createContext(context);
            httpContext.setHandler(exchange -> {
                try {
                    RestExchange restExchange = new RestExchange(exchange, mapper);
                    Function<RestExchange, Object> handler = handlers.get(restExchange.getMethod());

                    if (handler == null) {
                        exchange.sendResponseHeaders(HttpStatus.METHOD_NOT_ALLOWED, 0);
                        exchange.close();
                        return;
                    }


                    String body = restExchange.getBody();
                    logger.finest(() -> ">> Req: " + restExchange);
                    if (body != null && !body.isEmpty()) logger.finest(restExchange::getBody);

                    Object response = handler.apply(restExchange);
                    writeResponseBody(exchange, response);

                    logger.finest("<< Res: " + response);
                } catch (Exception e) {
                    logger.severe(() -> e.getMessage() + "\n"
                            + Arrays.stream(e.getStackTrace())
                            .map(Objects::toString)
                            .collect(Collectors.joining("\n")));
                }
            });
        });

        this.server.start();
        logger.info("Server starting at " + port);
    }

    /**
     * Store a context with multiple handlers.
     *
     * @param context  The context path
     * @param handlers The handlers with methods to use. See {@link #get(Function)}, {@link #post(Function)}
     * @return This server
     */
    @SafeVarargs
    public final RestServer nest(String context, Pair<HttpMethod, Function<RestExchange, Object>>... handlers) {
        for (Pair<HttpMethod, Function<RestExchange, Object>> handler : handlers) {
            addHandler(handler.getFirst(), context, handler.getSecond());
        }
        return this;
    }

    private RestServer addHandler(HttpMethod method, String context, Function<RestExchange, Object> handler) {
        logger.info("Mapping " + method + " " + context);
        getOrPutHandler(context).put(method, handler);
        return this;
    }

    private Map<HttpMethod, Function<RestExchange, Object>> getOrPutHandler(String context) {
        Map<HttpMethod, Function<RestExchange, Object>> handlersMap = this.handlers.get(context);
        if (handlersMap == null) {
            this.handlers.put(context, new HashMap<>());
            handlersMap = this.handlers.get(context);
        }
        return handlersMap;
    }

    private void writeResponseBody(HttpExchange exchange, Object response) throws IOException {
        String jsonResponse = mapper.writeValueAsString(response);
        exchange.getResponseHeaders().set("Content-Type", "Application/json");
        exchange.sendResponseHeaders(HttpStatus.OK, jsonResponse.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes());
        os.close();
    }

    private void init() {
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.handlers = new HashMap<>();
    }

    /**
     * Create a get method
     *
     * @param handler the get handler
     * @return the handler to be nested
     */
    public static Pair<HttpMethod, Function<RestExchange, Object>> get(Function<RestExchange, Object> handler) {
        return new Pair<>(HttpMethod.GET, handler);
    }

    /**
     * Create a post method
     *
     * @param handler the post handler
     * @return the handler to be nested
     */
    public static Pair<HttpMethod, Function<RestExchange, Object>> post(Function<RestExchange, Object> handler) {
        return new Pair<>(HttpMethod.POST, handler);
    }

    /**
     * Create a delete method
     *
     * @param handler the post handler
     * @return the handler to be nested
     */
    public static Pair<HttpMethod, Function<RestExchange, Object>> delete(Function<RestExchange, Object> handler) {
        return new Pair<>(HttpMethod.DELETE, handler);
    }
}
