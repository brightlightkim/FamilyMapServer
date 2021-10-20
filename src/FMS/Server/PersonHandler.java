package Server;

import Result.PeopleResult;
import Result.PersonResult;
import Service.PersonService;
import com.sun.net.httpserver.HttpExchange;

import Error.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class PersonHandler extends Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("get")){
                //take out "/person" part
                PersonService service = new PersonService();
                PeopleResult peopleResult = new PeopleResult();
                PersonResult personResult = new PersonResult();
                boolean one = false;

                //check if it's for all people or not
                if (exchange.getRequestURI().toString().length() == 7){
                    //pass it to the parameter as an auth token.
                    //1. valid
                    //2. get the username of the authtoken table
                    //3. get username to find all the people.
                    //4. make new function in the personDAO
                    //   select person where associated username = username..
                    //   10-30 people >> find all persons.. >> back to clients
                    peopleResult = service.findAllPeople();
                }
                else {
                    String personID = exchange.getRequestURI().toString().substring(7);
                    personResult = service.findPerson(personID);
                    one = true;
                }
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody= new OutputStreamWriter(exchange.getResponseBody());

                if (one){
                    gson.toJson(peopleResult, resBody);
                }
                else{
                    gson.toJson(personResult, resBody);
                }

                resBody.close();
                success = true;
            }
            if (!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
