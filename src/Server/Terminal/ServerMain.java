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
    private static ServerSocket server = null;

    public static Vector<NguoiDungDTO> users = new Vector<>();  // danh sách user online
    public static Vector<NguoiDungDTO> users_waitting = new Vector<>(); // danh sách user chờ game
    public static Vector<Room> waittingRooms = new Vector<>();  // danh sách phòng chờ game
    public static Vector<Worker> workers = new Vector<>();
    public static Vector<RoomWorker> roomWorkers = new Vector<>();
    public static ExecutorService executorRoom;
    public static ExecutorService executor;

    public ServerMain() throws IOException {
        port = ServerConfig.PORT;
        executor = Executors.newFixedThreadPool(ServerConfig.NUM_THREAD);
        executorRoom = Executors.newFixedThreadPool(ServerConfig.NUM_THREAD);

        try {
            server = new ServerSocket(port);
            System.out.println("Server binding at port " + port);
            System.out.println("Waiting for client...");
            GuiQuery gui = new GuiQuery();
            gui.setVisible(true);
            while (true) {
                Worker client = new Worker(server.accept());
                executor.execute(client);
                workers.add(client);
                gui.useronl();
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
