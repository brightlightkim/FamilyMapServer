package Server;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import Model.AuthToken;
import Result.PeopleResult;
import Result.PersonResult;
import Service.PersonService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import Error.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class PersonHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")) {
                //take out "/person" part
                PersonService service = new PersonService();
                PeopleResult peopleResult = new PeopleResult();
                PersonResult personResult = new PersonResult();
                boolean one = false;
                //check if it's for all people or not
                if (exchange.getRequestURI().toString().length() == 7) {
                    //TODO: Check this function out.
                    peopleResult = getPeopleResult(exchange, service);
                } else {
                    String personID = exchange.getRequestURI().toString().substring(8);
                    personResult = service.findPerson(personID);
                    one = true;
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());

                if (one) {
                    gson.toJson(peopleResult, resBody);
                } else {
                    gson.toJson(personResult, resBody);
                }

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

    private PeopleResult getPeopleResult(HttpExchange exchange, PersonService service) throws DataAccessException {
        //pass it to the parameter as an auth token.
        Headers reqHeaders = exchange.getRequestHeaders();
        //1. valid
        if (reqHeaders.containsKey("Authorization")) {
            String authToken = reqHeaders.getFirst("Authorization");
            String userName = getUsernameByToken(authToken);
            if (userName == null){
                return new PeopleResult("No Token that match", false);
            }
            return service.findAllPeople(userName);
        }
        else{
            return new PeopleResult("No Authorization Token", false);
        }
    }

    private String getUsernameByToken(String token) throws DataAccessException {
        Database db = new Database();
        AuthToken desiredToken = new AuthTokenDAO(db.openConnection()).find(token);
        if (token == null) {
            return null;
        } else {
            return desiredToken.getUsername();
        }
    }
}
