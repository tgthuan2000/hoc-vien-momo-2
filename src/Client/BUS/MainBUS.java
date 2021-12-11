package Client.BUS;

import Client.Status;
import Client.Thread.WattingGame;
import Shares.Key;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainBUS {

    private ExecutorService executor;

    public MainBUS() {
    }

    public int playgame() {
        try {
            BUS.writeLine(Key.PLAY_GAME);
            BUS.flush();

            if (BUS.readLine().equals(Key.WAITTING_GAME)) {
                executor = Executors.newFixedThreadPool(1);
                executor.execute(new WattingGame(BUS.socket));
                return Status.OK;
            }
            return Status.FAILD;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public int canclegame() {
        try {
            BUS.writeLine(Key.CANCLE_GAME);
            BUS.flush();

            if (BUS.readLine().equals(Key.ACCEPT_CANCLE_GAME)) {
                executor.shutdown();
                return Status.OK;
            }
            return Status.FAILD;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
}
