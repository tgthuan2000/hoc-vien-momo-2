package Server.Terminal;

import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomWorker implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String roomId;
    private final NguoiDungDTO player1;
    private final NguoiDungDTO player2;

    public RoomWorker(Socket s, String roomId, NguoiDungDTO player1, NguoiDungDTO player2) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        this.roomId = roomId;
        this.player1 = player1;
        this.player2 = player2;

    }

    private void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    private void writeLine(int num) throws IOException {
        out.write(num + "\n");
    }

    private void writeLine(boolean b) throws IOException {
        out.write(b + "\n");
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted room game");
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
            getInfoUser2();
            while (true) {
                try {
                    switch (in.readLine()) {

                    }
                } catch (IOException ex) {
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(RoomWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RoomWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getInfoUser2() throws IOException {
        writeLine(Key.INFO_USER_2);
        writeLine(player2.getTenNguoiDung());
        out.flush();
    }
}
