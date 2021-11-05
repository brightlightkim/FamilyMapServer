package Server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Handler implements HttpHandler {
    boolean success = false;
    Gson gson = new Gson();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }

    public void sendRightHttpResponse(HttpExchange exchange, boolean success) throws IOException {
        try {
            if (success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("Error while sending HTTP response");
        }
    }

    public void sendRightHttpResponse(HttpExchange exchange, boolean success1, boolean success2) throws IOException {
        try {
            if (success1 || success2) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            throw new IOException("Error while sending HTTP response");
        }
    }
}
