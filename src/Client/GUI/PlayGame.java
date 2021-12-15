package Client.GUI;

import Client.BUS.BUS;
import Shares.Key;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PlayGame extends javax.swing.JFrame {

    private static boolean isClick;
    public static String dapAn;
    private static int soCau;
    private static int thGian;

    public PlayGame() {
        try {
            UIManager.setLookAndFeel(new com.jtattoo.plaf.graphite.GraphiteLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLocationRelativeTo(null);
        this.setTitle("Game");
        initComponents();
        Form.playGame = this;
        soCau = 1;
        isClick = true;
        initData();
    }

    private void initData() {
        lbUser1.setText(BUS.user.getTenNguoiDung());
        lbUser2.setText(BUS.user2.getTenNguoiDung());
        getCau();
        setSTTCau();
    }

    public static void getCau() {
        lblCauHoi.setText(BUS.cauHoi);
        btnA.setText(BUS.dsDapAn.get(0));
        btnB.setText(BUS.dsDapAn.get(1));
        btnC.setText(BUS.dsDapAn.get(2));
        btnD.setText(BUS.dsDapAn.get(3));

    }

//    public static void setThoiGian() {
//        try {
//            thGian = BUS.thoiGian;
//            while (thGian > 0) {
//                if (!isClick) {
//                    break;
//                }
//                TimeUnit.MILLISECONDS.sleep(1000);
//                lbTime.setText(--thGian + "");
//                System.out.println("thoi gian: " + thGian);
//            }
//            if (isClick) {
////            BUS.writeLine(Key.TIMEOUT);
////            BUS.flush();
//            }
//        } catch (Exception ex) {
//        }
//    }
    public static void setSTTCau() {
        lblSTTCau.setText("Câu " + soCau++ + "/" + BUS.soCau);
    }

    public static void refresh() {
        btnA.setFont(new java.awt.Font("Lucida Grande", 0, 14));
        btnB.setFont(new java.awt.Font("Lucida Grande", 0, 14));
        btnC.setFont(new java.awt.Font("Lucida Grande", 0, 14));
        btnD.setFont(new java.awt.Font("Lucida Grande", 0, 14));

        btnA.setBackground(new java.awt.Color(240, 240, 240));
        btnB.setBackground(new java.awt.Color(240, 240, 240));
        btnC.setBackground(new java.awt.Color(240, 240, 240));
        btnD.setBackground(new java.awt.Color(240, 240, 240));

        isClick = true;
        dapAn = "";
        setSTTCau();
        refreshDiem();
    }

    public static void showScore() {
        lblScoreAnswer.setText("Số điểm câu này là: " + BUS.diem + " điểm");
    }

    public static void refreshDiem() {
        lblScoreAnswer.setText("");
    }

    public static void setTotalScore() {
        lbScore1.setText(Integer.toString(BUS.tongDiem));
    }

    public static void setTotalScoreUser2() {
        lbScore2.setText(Integer.toString(BUS.tongDiemUser2));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbTime = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar2 = new javax.swing.JProgressBar();
        lbUser1 = new javax.swing.JLabel();
        lbUser2 = new javax.swing.JLabel();
        lbScore2 = new javax.swing.JLabel();
        lbScore1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblCauHoi = new javax.swing.JLabel();
        lblSTTCau = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnB = new javax.swing.JButton();
        btnC = new javax.swing.JButton();
        btnD = new javax.swing.JButton();
        btnA = new javax.swing.JButton();
        lblScoreAnswer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        lbTime.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTime.setText("10s");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        jProgressBar1.setBackground(new java.awt.Color(0, 204, 204));

        jProgressBar2.setBackground(new java.awt.Color(0, 153, 153));

        lbUser1.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        lbUser1.setText("User 1");

        lbUser2.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        lbUser2.setText("User 2");

        lbScore2.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        lbScore2.setText("Score 2");

        lbScore1.setFont(new java.awt.Font("Lucida Grande", 3, 14)); // NOI18N
        lbScore1.setText("Score 1");

        jPanel3.setBackground(new java.awt.Color(102, 204, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        lblCauHoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCauHoi.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblCauHoi.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblSTTCau.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblSTTCau.setText("Câu 1/3");
        lblSTTCau.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(lblCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblSTTCau, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSTTCau, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 204, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        btnB.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btnB.setText("B");
        btnB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBMouseClicked(evt);
            }
        });

        btnC.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btnC.setText("C");
        btnC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCMouseClicked(evt);
            }
        });

        btnD.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btnD.setText("D");
        btnD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDMouseClicked(evt);
            }
        });

        btnA.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        btnA.setText("A");
        btnA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnB, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(btnD, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnA, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnA, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnB, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnD, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbUser1)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lbScore1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbUser2, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(lbScore2)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(lblScoreAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbUser1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbScore1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbUser2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbScore2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21)
                .addComponent(lblScoreAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMouseClicked
        sendRequest(BUS.dsDapAn.get(2), btnC);
    }//GEN-LAST:event_btnCMouseClicked

    private void btnAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMouseClicked
        sendRequest(BUS.dsDapAn.get(0), btnA);
    }//GEN-LAST:event_btnAMouseClicked

    private void btnBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMouseClicked
        sendRequest(BUS.dsDapAn.get(1), btnB);
    }//GEN-LAST:event_btnBMouseClicked

    private void btnDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDMouseClicked
        sendRequest(BUS.dsDapAn.get(3), btnD);
    }//GEN-LAST:event_btnDMouseClicked

    private void sendRequest(String dapAn, JButton button) {
        try {
            if (isClick) {
                this.dapAn = dapAn;
                button.setFont(new java.awt.Font("Lucida Grande", 1, 14));
                button.setBackground(new java.awt.Color(255, 153, 102));

                BUS.writeLine(Key.GUI_DAPAN);
                BUS.writeLine(dapAn);
                BUS.flush();
                isClick = false;
            }
        } catch (IOException ex) {
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnA;
    public static javax.swing.JButton btnB;
    public static javax.swing.JButton btnC;
    public static javax.swing.JButton btnD;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private static javax.swing.JLabel lbScore1;
    private static javax.swing.JLabel lbScore2;
    private static javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbUser1;
    private javax.swing.JLabel lbUser2;
    public static javax.swing.JLabel lblCauHoi;
    private static javax.swing.JLabel lblSTTCau;
    private static javax.swing.JLabel lblScoreAnswer;
    // End of variables declaration//GEN-END:variables
}
