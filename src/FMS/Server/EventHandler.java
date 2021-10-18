package Server;

import java.io.*;
import java.net.*;
import java.util.Locale;

import com.sun.net.httpserver.*;

public class EventHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                Headers reqHeaders = exchange.getRequestHeaders();
            }
            if (!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
