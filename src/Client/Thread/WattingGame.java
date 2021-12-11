package Client.Thread;

import Shares.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class WattingGame implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public WattingGame(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    private void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    @Override
    public void run() {
        try {
            if (in.readLine().equals(Key.ACCEPT_GAME)) {
                System.out.println("Chấp nhận game");
                int rs = JOptionPane.showConfirmDialog(null, "Chấp nhận vào game!!!");
                if (rs == JOptionPane.YES_OPTION) {
                    writeLine(Key.OK_ACCEPT_GAME);
                    out.flush();
                    in.close();
                    out.close();
                    socket.close();
                } else {
                    writeLine(Key.NO_ACCEPT_GAME);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WattingGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
