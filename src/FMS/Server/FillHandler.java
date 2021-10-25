package Server;

import Result.FillResult;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;

import Error.DataAccessException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.Locale;

public class FillHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                //TODO: Check this number out.
                String[] parsedString  = exchange.getRequestURI().toString().split("/");
                String username = parsedString[1];
                int generations = requestedNumberOfGenerations(parsedString[parsedString.length-1]);
                FillResult result = new FillService().fillResult(username, generations);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                exchange.getResponseBody().close();
                success = true;
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private int requestedNumberOfGenerations(String userInput){

        try{
            int value = Integer.parseInt(userInput);
            return value;
        }
        catch (NumberFormatException e){
            return 4; //This is the default setting.
        }
    }
}
