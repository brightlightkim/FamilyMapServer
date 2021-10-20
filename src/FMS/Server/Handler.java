package Server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class Handler implements HttpHandler {
    boolean success = false;
    Gson gson = new Gson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }
}
