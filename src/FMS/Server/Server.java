package Server;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    private void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/games/list", new ListGamesHandler());

        server.createContext("/routes/claim", new ClaimRouteHandler());

        server.createContext("/", new FileHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    /**
     * main method for the server program
     * @param args one command-line argument, which is the port numbe
     * on which the server should accept incoming client connections.
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
