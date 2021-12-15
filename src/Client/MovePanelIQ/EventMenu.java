package Client.MovePanelIQ;

import Client.GUI.QuestionIQ.Cau1;
import Client.GUI.QuestionIQ.Cau10;
import Client.GUI.QuestionIQ.Cau2;
import Client.GUI.QuestionIQ.Cau2;
import Client.GUI.QuestionIQ.Cau3;
import Client.GUI.QuestionIQ.Cau4;
import Client.GUI.QuestionIQ.Cau5;
import Client.GUI.QuestionIQ.Cau6;
import Client.GUI.QuestionIQ.Cau7;
import Client.GUI.QuestionIQ.Cau8;
import Client.GUI.QuestionIQ.Cau9;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventMenu {

    private JPanel pnRoot;
    private ArrayList<Danhmuc> menu = null;

    public EventMenu(JPanel pnRoot) {
        this.pnRoot = pnRoot;
    }

    public void setMenu(ArrayList<Danhmuc> menu) {
        this.menu = menu;
        for (Danhmuc danhmuc : menu) {
            danhmuc.getBtnName().addMouseListener(new Events(danhmuc.getKindOfScreen(), danhmuc.getBtnName()));
        }
    }

    class Events implements MouseListener {

        private JPanel pnNode;
        private String kind;
        private JButton btnName;

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


            }
            pnRoot.removeAll();
            pnRoot.setLayout(new BorderLayout());
            pnRoot.add(pnNode);
            pnRoot.validate();
            pnRoot.repaint();
            setBackgroudSeclect(kind);
        }

        public Events(String kind, JButton btnName){
            this.kind = kind;
            this.btnName = btnName;
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

    private void setBackgroudSeclect(String select) {
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
