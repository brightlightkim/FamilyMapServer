package Server;

import Error.DataAccessException;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class LoginHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                Reader reqData = new InputStreamReader(exchange.getRequestBody());
                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);
                LoginService service = new LoginService();
                LoginResult result = service.login(request);
                sendRightHttpResponse(exchange, result.isSuccess());
                Writer resBody= new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
            }
            else {
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