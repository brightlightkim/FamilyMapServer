package Server;

import Result.AllEventResult;
import Result.EventResult;
import Service.EventService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.Locale;

public class EventHandler extends Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                EventService service = new EventService();
                EventResult eventResult = new EventResult();
                AllEventResult allEventResult = new AllEventResult();
                boolean one = false;

                if (exchange.getRequestURI().toString().length() == 6){
                    //for all people.

                }
                else {
                    String eventID = exchange.getRequestURI().toString().substring(6);
                    eventResult = service.requestEvent();
                    one = true;
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

                if (one) {
                    gson.toJson(eventResult, resBody);
                }
                else{
                    gson.toJson(allEventResult, resBody);
                }
                resBody.close();
                exchange.getResponseBody().close();
                success = true;
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
}
