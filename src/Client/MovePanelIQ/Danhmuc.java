/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MovePanelIQ;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class Danhmuc {
    private String kindOfScreen;
    private JPanel pnName;
    private JLabel lbName;
    
    Danhmuc(){
    }
    
    public Danhmuc(String kindOfScreen, JPanel pnName, JLabel lbName) {
        this.kindOfScreen = kindOfScreen;
        this.pnName = pnName;
        this.lbName = lbName;
    }

    public String getKindOfScreen() {
        return kindOfScreen;
    }

    public JPanel getPnName() {
        return pnName;
    }

    public JLabel getLbName() {
        return lbName;
    }

    public void setKindOfScreen(String kindOfScreen) {
        this.kindOfScreen = kindOfScreen;
    }

    public void setPnName(JPanel pnName) {
        this.pnName = pnName;
    }

    public void setLbName(JLabel lbName) {
        this.lbName = lbName;
    }
    
}
