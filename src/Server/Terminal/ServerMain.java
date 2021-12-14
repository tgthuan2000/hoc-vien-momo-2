package Server.Terminal;

import Server.GUI.GuiQuery;
import Shares.DTO.NguoiDungDTO;
import Shares.ServerConfig;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    public final int port;
    public final int numThread;
    private static ServerSocket server = null;
    public static Vector<NguoiDungDTO> users = new Vector<>();
    public static Vector<NguoiDungDTO> users_watting = new Vector<>();
    public static Vector<Worker> workers = new Vector<>();

    public ServerMain() throws IOException {
        port = ServerConfig.PORT;
        numThread = ServerConfig.NUM_THREAD;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            new GuiQuery().setVisible(true);
            while (true) {
                Worker client = new Worker(server.accept());
                executor.execute(client);
                workers.add(client);
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
