package Server;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

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

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        //TODO: Need more functions here for getting the username and generations.
        server.createContext("/fill/[username]/{generations}", new ListGamesHandler());
        server.createContext("/load", new ListGamesHandler());
        //TODO: Need more functions to find personID.
        server.createContext("/person", new PersonHandler());
        server.createContext("/clear", new ClearHandler());
        //TODO: Need more functions to find eventID.
        server.createContext("/event/[eventID]", new ListGamesHandler());
        server.createContext("/event", new ListGamesHandler());

        server.createContext("/", new FileHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    /**
     * main method for the server program
     * @param args one command-line argument, which is the port number
     * on which the server should accept incoming client connections.
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
