package Client.MovePanelIQ;

import java.awt.Button;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Danhmuc {

    private String kindOfScreen;
    private JButton btnName;

    Danhmuc() {
    }

    public Danhmuc(String kindOfScreen, JButton btnName){
        this.kindOfScreen = kindOfScreen;
        this.btnName = btnName;
        
    }

    public String getKindOfScreen() {
        return kindOfScreen;
    }

   
    public void setKindOfScreen(String kindOfScreen) {
        this.kindOfScreen = kindOfScreen;
    }

    public JButton getBtnName() {
        return btnName;
    }

    public void setBtnName(JButton btnName) {
        this.btnName = btnName;
    }
}
