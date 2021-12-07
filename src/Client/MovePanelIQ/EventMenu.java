/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.MovePanelIQ;

import Client.MovePanelIQ.Danhmuc;
import Client.QuestionIQ.Cau8;
import Client.QuestionIQ.Cau5;
import Client.QuestionIQ.Cau2;
import Client.QuestionIQ.Cau4;
import Client.QuestionIQ.Cau10;
import Client.QuestionIQ.Cau3;
import Client.QuestionIQ.Cau6;
import Client.QuestionIQ.Cau1;
import Client.QuestionIQ.Cau7;
import Client.QuestionIQ.Cau9;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class EventMenu {
    private JPanel pnRoot; 
    private ArrayList<Danhmuc> menu = null;

    public EventMenu(JPanel pnRoot) {
        this.pnRoot = pnRoot;
    }

    public void setMenu(ArrayList<Danhmuc> menu) {
        this.menu = menu;
        for (Danhmuc danhmuc : menu) {
             danhmuc.getPnName().addMouseListener(new Events(danhmuc.getKindOfScreen(), danhmuc.getPnName(), danhmuc.getLbName()));
        }
    }
    class Events implements MouseListener {

        private JPanel pnNode;
        private String kind;
        private JPanel pnName;
        private JLabel lbName;

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
               case "cau1":
                    pnNode = new Cau1();
                    break; 
                case "cau2":
                    pnNode = new Cau2();
                    break;
                case "cau3":
                    pnNode = new Cau3();
                    break;
                case "cau4":
                    pnNode = new Cau4();
                    break;
                case "cau5":
                    pnNode = new Cau5();
                    break;
                case "cau6":
                    pnNode = new Cau6();
                    break;
                case "cau7":
                    pnNode = new Cau7();
                    break;
                case "cau8":
                    pnNode = new Cau8();
                    break;
                case "cau9":
                    pnNode = new Cau9();
                    break;
                case "cau10":
                    pnNode = new Cau10();
                    break;
//                    break;
//                case "nhanvien":
//                    pnNode = new NhanVienGUII();
//                    break; 
//                case "chucvu":
//                    pnNode = new ChucVuGUII();
//                    break; 
//                 case "khachhang":
//                    pnNode = new KhachHangGUII();
//                    break; 
//                 case "nhacungcap":
//                    pnNode = new NhaCungCapGUII();
//                    break; 
//                 case "hoadon":
//                    pnNode = new HoaDonGUI_CTHoaDonGUI();
//                    break; 
//                 case "phieunhap":
//                    pnNode = new PhieuNhapGUI_CTPhieuNhapGUI();
//                    break; 
//                 case "khuyenmai":
//                    pnNode = new KhuyenMaiGUI_CTKhuyenMaiGUI();
//                    break; 
//                 case "thongke":
//                    pnNode = new ThongkeGUII();
//                    break; 
                
        }
            pnRoot.removeAll();
            pnRoot.setLayout(new BorderLayout());
            pnRoot.add(pnNode);
            pnRoot.validate();
            pnRoot.repaint();
            setBackgroudSeclect(kind);            
        }

        public Events(String kind, JPanel pnName, JLabel lbName) {
            this.kind = kind;
            this.pnName = pnName;
            this.lbName = lbName;
        }    

        @Override
        public void mousePressed(MouseEvent e) {
//            selectedScreen = kind;
//            pnName.setBackground(new Color(44,62,80));            
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
    
        }

        @Override
        public void mouseExited(MouseEvent e) {
        
        }
    }
    private void setBackgroudSeclect(String select){
//       for(Danhmuc danhmuc : menu) {
//           if(!danhmuc.getKindOfScreen().equalsIgnoreCase(select)) {
//               danhmuc.getPnName().setBackground(new Color(1,50,67));
//               danhmuc.getLbName().setBackground(new Color(1,50,67));
//           } else {
//               danhmuc.getLbName().setBackground(new Color(148,124,176));
//               danhmuc.getPnName().setBackground(new Color(148,124,176));
//           }
//       }
    }
}
