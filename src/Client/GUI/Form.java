package Client.GUI;

import Shares.DTO.NguoiDungDTO;

public class Form {

    public static InterfaceIQ interfaceIQ;
    public static Login login;
    public static Main main;
    public static OTP oTP;
    public static PlayGame playGame;
    public static Register register;

    public static void showInterfaceIQ() {
        if (interfaceIQ == null) {
            interfaceIQ = new InterfaceIQ();
        }
        interfaceIQ.setVisible(true);
    }

    public static void hideInterfaceIQ() {
        if (interfaceIQ != null) {
        }
        interfaceIQ.setVisible(false);
    }

    public static void newInterfaceIQ() {
        interfaceIQ = new InterfaceIQ();
        interfaceIQ.setVisible(true);
    }

    public static void showLogin() throws Exception {
        if (login == null) {
            login = new Login();
        }
        login.setVisible(true);
    }

    public static void hideLogin() {
        if (login != null) {
        }
        login.setVisible(false);
    }

    public static void showMain() throws Exception {
        if (main == null) {
            main = new Main();
        }
        main.setVisible(true);
    }

    public static void newMain() throws Exception {
        main = new Main();
        main.setVisible(true);
    }

    public static void hideMain() {
        if (main != null) {
        }
        main.setVisible(false);
    }

    public static void showOTP(NguoiDungDTO nd) throws Exception {
        if (oTP == null) {
            oTP = new OTP(nd);
        }
        oTP.setVisible(true);
    }

    public static void hideOTP() {
        if (oTP != null) {
        }
        oTP.setVisible(false);
    }

    public static void showPlayGame() throws Exception {
        if (playGame == null) {
            playGame = new PlayGame();
        }
        playGame.setVisible(true);
    }

    public static void hidePlayGame() {
        if (playGame != null) {
        }
        playGame.setVisible(false);
    }

    public static void newPlayGame() {
        playGame = new PlayGame();
        playGame.setVisible(true);
    }

    public static void showRegister() throws Exception {
        if (register == null) {
            register = new Register();
        }
        register.setVisible(true);
    }

    public static void hideRegister() {
        if (register != null) {
        }
        register.setVisible(false);
    }

    static void newRegister() {
        register = new Register();
        register.setVisible(true);
    }

}
