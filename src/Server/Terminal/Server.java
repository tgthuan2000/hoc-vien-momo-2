package Server.Terminal;

import Shares.ServerConfig;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public final int port;
    public final int numThread;
    private static ServerSocket server = null;
    public static Vector<String> users = new Vector<>();

    public Server() throws IOException {
        port = ServerConfig.PORT;
        numThread = ServerConfig.NUM_THREAD;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            while (true) {
                executor.execute(new Worker(server.accept()));
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (server != null) {
                server.close();
            }
        }
    }
}
