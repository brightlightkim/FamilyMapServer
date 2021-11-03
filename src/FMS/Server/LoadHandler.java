package Server;

import Error.DataAccessException;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

public class LoadHandler extends Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals("post")){
                Reader reqData = new InputStreamReader(exchange.getRequestBody());
                LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);;
                LoadService service = new LoadService();
                LoadResult result = service.load(request);
                if (result.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
                exchange.getResponseBody().close();
            }
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
