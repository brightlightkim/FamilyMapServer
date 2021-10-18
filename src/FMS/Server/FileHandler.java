package Server;
import java.io.*;
import java.net.*;
import java.util.Locale;

import com.sun.net.httpserver.*;

public class FileHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                //set the URL
                String urlPath = exchange.getRequestURI().toString();

            }
            else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }
            if (!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

}
