package Server;

import Exception.DataAccessException;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class LoginHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                Reader reqData = new InputStreamReader(exchange.getRequestBody());
                LoginRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);
                LoginService service = new LoginService();
                LoginResult result = service.login(request);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Writer resBody= new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
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