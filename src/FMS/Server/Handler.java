package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class Handler implements HttpHandler {
    boolean success = false;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }
}
