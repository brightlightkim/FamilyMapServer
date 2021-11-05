package Server;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import Error.DataAccessException;
import Model.AuthToken;
import Result.EventsResult;
import Result.EventResult;
import Service.EventService;
import com.sun.net.httpserver.Headers;
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
                EventsResult eventsResult = new EventsResult();
                boolean one = false;

                if (exchange.getRequestURI().toString().length() == 6){
                    //for all people.
                    eventsResult = getAllEventResult(exchange, service);
                }
                else {
                    eventResult = getEventResult(exchange, service);
                    one = true;
                }

                sendRightHttpResponse(exchange, eventResult.isSuccess(), eventsResult.isSuccess());

                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

                if (one) {
                    gson.toJson(eventResult, resBody);
                }
                else{
                    gson.toJson(eventsResult, resBody);
                }

                resBody.close();
                exchange.getResponseBody().close(); //What triggers everything up (ends of the handler)
            }
            else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private EventsResult getAllEventResult(HttpExchange exchange, EventService service) throws DataAccessException {
        Headers reqHeaders = exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Authorization")) {
            String authToken = reqHeaders.getFirst("Authorization");
            if (authToken.length() == 0){
                return new EventsResult("Error: No Authorization Token input", false);
            }
            String userName = getUsernameByToken(authToken);
            if (userName == null){
                return new EventsResult("Error: No Token that match", false);
            }
            return service.allEventResult(userName);
        }
        else{
            return new EventsResult("Error: No Authorization Token", false);
        }
    }

    private EventResult getEventResult(HttpExchange exchange, EventService service) throws DataAccessException{
        Headers reqHeaders = exchange.getRequestHeaders();

        if (reqHeaders.containsKey("Authorization")) {
            String authToken = reqHeaders.getFirst("Authorization");
            if (authToken.length() == 0){
                return new EventResult("Error: No Authorization token input", false);
            }
            String userName = getUsernameByToken(authToken);
            if (userName == null){
                return new EventResult("Error: No Token that match", false);
            }
            String eventID = exchange.getRequestURI().toString().substring(7);
            EventResult desiredEvent = service.requestEvent(eventID);
            if (!desiredEvent.getAssociatedUsername().equals(userName)){
                return new EventResult("Error: Event's username does not match with authorized token", false);
            }
            return desiredEvent;
        }
        else{
            return new EventResult("Error: No Authorization Token", false);
        }
    }

    private String getUsernameByToken(String token) throws DataAccessException {
        Database db = new Database();
        db.getConnection();
        AuthToken desiredToken = new AuthTokenDAO(db.getConnection()).find(token);
        db.closeConnection(true);
        if (desiredToken == null) {
            return null;
        } else {
            return desiredToken.getUsername();
        }
    }
}
