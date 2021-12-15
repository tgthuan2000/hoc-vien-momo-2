package Server.GUI;

import Server.BUS.GuiQueryBUS;
import Server.Terminal.ServerMain;
import Server.Terminal.Worker;
import Shares.DTO.NguoiDungDTO;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GuiQuery extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel model1 = new DefaultTableModel();
    DefaultTableModel model3 = new DefaultTableModel();
    DefaultTableModel model4 = new DefaultTableModel();
    GuiQueryBUS bus = new GuiQueryBUS();
//    ServerMain servermain = null;
    Vector<NguoiDungDTO> nguoidungs = null;
    public String i = null;
    public int max = 0;
    public int max1 = 0;
    public int max2 = 0;
    public ArrayList<Integer> list = new ArrayList<>();
    public ArrayList<NguoiDungDTO> listnguoidung;
    public static Vector<Worker> workers = null;

    public GuiQuery() {
        initComponents();
        this.setLocationRelativeTo(null);
        model = (DefaultTableModel) tb1.getModel();
        model1 = (DefaultTableModel) tb2.getModel();
        model3 = (DefaultTableModel) tb3.getModel();
        model4 = (DefaultTableModel) tb4.getModel();
        readinfofromsql();
        readinfo();
        readinfo1();
        readinfo2();
        numberUser();
    }

    public void readinfofromsql() {

        for (NguoiDungDTO nguoi : bus.readinfo()) {
            model1.addRow(new Object[]{
                nguoi.getUsername(), nguoi.getTenNguoiDung(), nguoi.getGioiTinh(), nguoi.isIsBlock()
            });
        }
        tb2.setModel(model1);
    }

    public void readinfo() {
        max();

        for (NguoiDungDTO nguoi : bus.readinfo()) {
            if (nguoi.getTongTran() == max) {
                model.addRow(new Object[]{
                    nguoi.getUsername()});

            }
        }
        tb1.setModel(model);
    }

    public void readinfo1() {
        max1();

        for (NguoiDungDTO nguoi : bus.readinfo()) {
            if (nguoi.getTongTranThang() == max1) {
                model3.addRow(new Object[]{
                    nguoi.getUsername()});

            }
        }
        tb3.setModel(model3);
    }

    public void readinfo2() {
        max2();

        for (NguoiDungDTO nguoi : bus.readinfo()) {
            if (nguoi.getChuoiThangMax() == max2) {
                model4.addRow(new Object[]{
                    nguoi.getUsername()});

            }
        }
        tb4.setModel(model4);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb3 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtsumuser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtuseronl = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtstatus = new javax.swing.JTextField();
        btnblock = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txttennguoidung = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtgender = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb4 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tb3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Người thắng nhiều trận nhất"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb3);

        jLabel1.setText("Username");

        jLabel2.setText("Người chơi đang online");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("THỐNG KÊ");

        tb2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "TenNguoiDung", "GioiTinh", "TrangThai"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Byte.class, java.lang.Byte.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb2);

        jLabel4.setText("Tổng người chơi");

        jLabel5.setText("Tên người dùng");

        btnblock.setText("Block");
        btnblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnblockActionPerformed(evt);
            }
        });

        jLabel6.setText("Trạng thái");

        jLabel7.setText("Giới Tính");

        jButton3.setText("Unblock");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tb1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Người tham gia nhiều trận nhất"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tb1);

        tb4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Người có chuỗi thắng dài nhất"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tb4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txttennguoidung, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                    .addComponent(txtusername))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtstatus))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtgender, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(btnblock, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtsumuser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(txtuseronl, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsumuser, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtuseronl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(7, 7, 7))
                    .addComponent(txtgender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtusername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnblock, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttennguoidung, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tb2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb2MouseClicked

        int i = tb2.getSelectedRow();
        if (i >= 0) {

            txtusername.setText(tb2.getModel().getValueAt(i, 0).toString());
            txttennguoidung.setText(tb2.getModel().getValueAt(i, 1).toString());
            txtgender.setText(tb2.getModel().getValueAt(i, 2).toString());
            txtstatus.setText(tb2.getModel().getValueAt(i, 3).toString());
            txtusername.setEnabled(false);
            txttennguoidung.setEnabled(false);
            txtgender.setEnabled(false);
            txtstatus.setEnabled(false);
        }

    }//GEN-LAST:event_tb2MouseClicked

    public void max() {

        for (NguoiDungDTO nguoi : bus.readnguoidung()) {
            if (nguoi.getTongTran() > max) {
                max = nguoi.getTongTran();
            }
        }

    }

    public int max1() {

        for (NguoiDungDTO nguoi : bus.readnguoidung()) {
            if (nguoi.getTongTranThang() > max1) {
                max1 = nguoi.getTongTranThang();
            }
        }
        return max1;
    }

    public void max2() {

        for (NguoiDungDTO nguoi : bus.readnguoidung()) {
            if (nguoi.getChuoiThangMax() > max2) {
                max2 = nguoi.getChuoiThangMax();
            }
        }

    }

    public String win() {

        StringBuilder buider = new StringBuilder();
        max();
        System.out.println(list);
        String winner = "";
        for (NguoiDungDTO nguoi : bus.readinfo()) {
            if (nguoi.getTongTran() == max) {
                winner = nguoi.getUsername();
                buider.append(winner);
            }
        }
        return buider.toString();
    }
    private void btnblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnblockActionPerformed

        NguoiDungDTO nguoi = new NguoiDungDTO();
        nguoi.setUsername(txtusername.getText());
        int i = tb2.getSelectedRow();
        if (txtstatus.getText().equals("false")) {
            btnblock.setEnabled(true);
            jButton3.setEnabled(false);
            if (i >= 0) {

                int pos = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn block user này?");

                if (pos == JOptionPane.YES_OPTION) {
                    txtstatus.setText("true");

                    nguoi.setIsBlock(Boolean.parseBoolean(txtstatus.getText()));
                    if (bus.isBlockUser(nguoi)) {
                        model1.setValueAt(nguoi.isIsBlock(), i, 3);

                        JOptionPane.showMessageDialog(null, "Block Thành Công");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Block Thất Bại");
                }
            }
        }

    }//GEN-LAST:event_btnblockActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        NguoiDungDTO nguoi = new NguoiDungDTO();
        nguoi.setUsername(txtusername.getText());
        int i = tb2.getSelectedRow();
        if (txtstatus.getText().equals("true")) {

            if (i >= 0) {

                int pos = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn unblock user này?");

                if (pos == JOptionPane.YES_OPTION) {
                    txtstatus.setText("false");

                    nguoi.setIsBlock(Boolean.parseBoolean(txtstatus.getText()));
                    if (bus.isBlockUser(nguoi)) {
                        model1.setValueAt(nguoi.isIsBlock(), i, 3);
                        JOptionPane.showMessageDialog(null, "Unblock Thành Công");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Unblock Thất Bại");
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public void numberUser() {
        int count = 0;
        ArrayList<String> list = new ArrayList<>();
        for (NguoiDungDTO nguoi : bus.readdsnguoidung()) {
            list.add(nguoi.getUsername());
            count++;
        }
        txtsumuser.setText(String.valueOf(count));
    }

    public void useronl() {
        workers = ServerMain.workers;
        txtuseronl.setText(String.valueOf(workers.size()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiQuery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiQuery().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnblock;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tb1;
    private javax.swing.JTable tb2;
    private javax.swing.JTable tb3;
    private javax.swing.JTable tb4;
    private javax.swing.JTextField txtgender;
    private javax.swing.JTextField txtstatus;
    private javax.swing.JTextField txtsumuser;
    private javax.swing.JTextField txttennguoidung;
    private javax.swing.JTextField txtusername;
    private javax.swing.JTextField txtuseronl;
    // End of variables declaration//GEN-END:variables
}
