package Client.BUS;

import Client.Status;
import Shares.Key;
import java.io.IOException;

public class MainBUS {

    public MainBUS() {
    }

    public int playgame() {
        try {
            BUS.writeLine(Key.PLAY_GAME);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public int canclegame() {
        try {
            BUS.writeLine(Key.CANCLE_GAME);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
}
