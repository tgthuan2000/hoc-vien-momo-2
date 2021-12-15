/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MovePanelIQ;

/**
 *
 * @author thanh
 */
public class CauDung {
    private String cauhoi;
    private int caudung;

    public CauDung() {
    }

    public CauDung(String cauhoi, int caudung) {
        this.cauhoi = cauhoi;
        this.caudung = caudung;
    }

    public String getCauhoi() {
        return cauhoi;
    }

    public void setCauhoi(String cauhoi) {
        this.cauhoi = cauhoi;
    }

    public int getCaudung() {
        return caudung;
    }

    public void setCaudung(int caudung) {
        this.caudung = caudung;
    }
    
    
}
