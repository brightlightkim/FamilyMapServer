package Server;

import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import Error.DataAccessException;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class ClearHandler extends Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                ClearService service = new ClearService();
                ClearResult result = service.clear();
                sendRightHttpResponse(exchange, result.isSuccess());
                Writer resBody= new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                success = true;
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
