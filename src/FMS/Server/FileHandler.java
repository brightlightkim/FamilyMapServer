package Server;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.Locale;

import com.sun.net.httpserver.*;

/**
 * File Handler Class that overrides HttpHandler handle method.
 * It returns index.html if the request is null, /, or "/index.html"
 */
public class FileHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                //set the URL
                String urlPath = exchange.getRequestURI().toString();
                if ((urlPath == null)||(urlPath == "/")||(urlPath == "/index.html")){
                    urlPath = "/index.html";
                }
                String filePath = "web" + urlPath;
                File file = new File(filePath);
                if (file.exists()){
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    success = true;
                }
                else{ //file is not found
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    exchange.getResponseBody().close();
                }
            }
            else{ //if it's not get function
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
