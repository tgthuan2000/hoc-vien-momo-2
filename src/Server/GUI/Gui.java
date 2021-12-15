/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.GUI;

import Server.BUS.ServerBUS;
import Shares.DTO.CauHinhDTO;
import Shares.DTO.CauHoiDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGAN DOAN
 */
public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel model1 = new DefaultTableModel();
    ServerBUS bus = new ServerBUS();
    public String i = null;
    ArrayList<CauHoiDTO> dschsudung = null;

    public Gui() {

        initComponents();
        this.setLocationRelativeTo(null);
        model = (DefaultTableModel) tb1.getModel();
        model1 = (DefaultTableModel) tb2.getModel();
        readconfromsql();
        readcauhoifromsql();

    }

    public void readconfromsql() {
        CauHinhDTO co = bus.readConfig();
        model.addRow(new Object[]{
            co.getSoLuongCauHoi(), co.getDiemTranDau(), co.getThoiGian()
        });

        tb1.setModel(model);
    }

    public void readcauhoifromsql() {
        dschsudung = bus.readcauhoi();
        for (CauHoiDTO cauhoi : dschsudung) {
            model1.addRow(new Object[]{
                cauhoi.getCauHoi(), cauhoi.getCauDung(), cauhoi.getCauSai1(), cauhoi.getCauSai2(), cauhoi.getCauSai3()
            });
        }
        tb2.setModel(model1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtnum = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txttime = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtcore = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb1 = new javax.swing.JTable();
        btnmodify = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txttrue = new javax.swing.JTextField();
        btnadd = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        false1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        false2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        false3 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb2 = new javax.swing.JTable();
        txtquestion = new javax.swing.JTextField();
        btnreset = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("guiServer");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Cấu Hình");

        jLabel9.setText("Số lượng");

        jLabel1.setText("Thời Gian");

        jLabel2.setText("Điểm trận đấu");

        tb1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SoLuongCauHoi", "DiemTranDau", "ThoiGian"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb1);

        btnmodify.setText("Sửa");
        btnmodify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodifyActionPerformed(evt);
            }
        });

        jLabel3.setText("Nhập câu hỏi");

        jLabel4.setText("true");

        btnadd.setText("Thêm CSDL");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        jLabel5.setText("false");

        jLabel6.setText("false");

        jLabel7.setText("false");

        tb2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CauHoi", "CauDung", "CauSai1", "CauSai2", "CauSai3"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane4.setViewportView(tb2);

        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btnsua.setText("Sửa");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnmodify, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcore, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(138, 138, 138)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addGap(20, 20, 20))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(323, 323, 323)
                        .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(false1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtquestion, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttrue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnadd)
                                .addGap(18, 18, 18)
                                .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnreset))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(false2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(false3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttime, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcore, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnum, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnmodify, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtquestion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttrue, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnsua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(false1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(false2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(false3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tb1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb1MouseClicked
        int i = tb1.getSelectedRow();
        if (i >= 0) {

            txtnum.setText(tb1.getModel().getValueAt(i, 0).toString());
            txtcore.setText(tb1.getModel().getValueAt(i, 1).toString());
            txttime.setText(tb1.getModel().getValueAt(i, 2).toString());

        }
    }//GEN-LAST:event_tb1MouseClicked

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean check() {
        boolean ischeck1 = true;
        try {
            if (txtnum.getText().equals("") || txtcore.getText().equals("") || txttime.getText().equals("")) {
                JOptionPane.showConfirmDialog(null, "Chưa điền đủ thông tin");
                ischeck1 = false;
            } else if (isNumeric(txttime.getText()) == false || isNumeric(txtcore.getText()) == false || isNumeric(txtnum.getText()) == false) {
                JOptionPane.showConfirmDialog(null, "Dữ liệu phải là số");
                ischeck1 = false;
            } else if (Integer.parseInt(txttime.getText()) < 0 || Integer.parseInt(txtcore.getText()) < 0 || Integer.parseInt(txtnum.getText()) < 0) {
                JOptionPane.showConfirmDialog(null, "Dữ liệu phải là số dương");
                ischeck1 = false;
            }
        } catch (Exception e) {
        }
        return ischeck1;
    }

    public boolean check1() {

        boolean ischeck1 = true;

        if (txtquestion.getText().length() == 0) {
            ischeck1 = false;

        }
        if (txttrue.getText().length() == 0) {

            ischeck1 = false;

        }
        if (false1.getText().length() == 0) {

            ischeck1 = false;

        }
        if (false2.getText().length() == 0) {

            ischeck1 = false;

        }
        if (false3.getText().length() == 0) {

            ischeck1 = false;

        }
        return ischeck1;
    }

    public CauHinhDTO textfiled() {
        CauHinhDTO conf = new CauHinhDTO();

        conf.setSoLuongCauHoi(Integer.parseInt(txtnum.getText()));

        conf.setDiemTranDau(Integer.parseInt(txtcore.getText()));

        conf.setThoiGian(Integer.parseInt(txttime.getText()));

        return conf;
    }

    public CauHoiDTO textfiledcauhoi(int i) {
        CauHoiDTO cauhoi = new CauHoiDTO();
        cauhoi.setId(i);
        cauhoi.setCauHoi(txtquestion.getText());

        cauhoi.setCauDung(txttrue.getText());

        cauhoi.setCauSai1(false1.getText());

        cauhoi.setCauSai2(false2.getText());

        cauhoi.setCauSai3(false3.getText());

        return cauhoi;
    }
    private void btnmodifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodifyActionPerformed

        if (check()) {
            CauHinhDTO config1 = textfiled();
            int i = tb1.getSelectedRow();
            if (i >= 0) {

                int pos = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn sửa trường này?");

                if (pos == JOptionPane.YES_OPTION) {

                    if (bus.changeconfig(config1)) {

                        model.setValueAt(config1.getSoLuongCauHoi(), i, 0);
                        model.setValueAt(config1.getDiemTranDau(), i, 1);
                        model.setValueAt(config1.getThoiGian(), i, 2);
                        reset();
                        JOptionPane.showMessageDialog(rootPane, "Sửa Thành Công");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sửa Thất Bại");
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sửa Thất Bại");
                }
            }
        }
    }//GEN-LAST:event_btnmodifyActionPerformed

    private void tb2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb2MouseClicked
        int i = tb2.getSelectedRow();
        if (i >= 0) {

            txtquestion.setText(tb2.getModel().getValueAt(i, 0).toString());
            txttrue.setText(tb2.getModel().getValueAt(i, 1).toString());
            false1.setText(tb2.getModel().getValueAt(i, 2).toString());
            false2.setText(tb2.getModel().getValueAt(i, 3).toString());
            false3.setText(tb2.getModel().getValueAt(i, 4).toString());

        }
    }//GEN-LAST:event_tb2MouseClicked

    public boolean kiemtratrung() {
        boolean flag = true;
        if (txttrue.getText().trim().equalsIgnoreCase(false1.getText().trim()) || false1.getText().trim().equalsIgnoreCase(false2.getText().trim())
                || false2.getText().trim().equalsIgnoreCase(false3.getText().trim()) || txttrue.getText().trim().equalsIgnoreCase(false2.getText().trim())
                || txttrue.getText().trim().equalsIgnoreCase(false3.getText().trim()) || false1.getText().trim().equalsIgnoreCase(false3.getText().trim())) {
            flag = false;
        } else {

            flag = true;

        }
        return flag;
    }

    public boolean kiemtratontai(CauHoiDTO cau) {
        for (CauHoiDTO cauhoi : dschsudung) {
            if (cauhoi.getCauHoi().equals(cau.getCauHoi())) {
                return false;
            }
        }
        return true;
    }
    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        boolean check1 = true;
        if (check1()) {
            CauHoiDTO cau = new CauHoiDTO();
            cau.setCauHoi(txtquestion.getText());
            cau.setCauDung(txttrue.getText());
            cau.setCauSai1(false1.getText());
            cau.setCauSai2(false2.getText());
            cau.setCauSai3(false3.getText());

            if (check1 == true) {
                int res = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn thêm câu hỏi này?");
                if (res == JOptionPane.YES_OPTION) {
                    if (kiemtratrung()) {
                        if (kiemtratontai(cau)) {
                            if (bus.addcauhoi(cau)) {
                                showResult();
                                reset1();
                                JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
                            } else {
                                JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Câu hỏi đã tồn tại");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Dữ liệu không được trùng nhau");
                    }

                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
        }
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed

        txtcore.setText("");
        txttime.setText("");

        txtnum.setText("");

        txtquestion.setText("");
        txttime.setText("");
        txttrue.setText("");
        false1.setText("");
        false2.setText("");
        false3.setText("");

    }//GEN-LAST:event_btnresetActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed

        int i = tb2.getSelectedRow();
        CauHoiDTO cauhoi2 = textfiledcauhoi(i + 1);
        if (i >= 0) {

            int pos = JOptionPane.showConfirmDialog(rootPane, "Bạn muốn sửa trường này?");

            if (pos == JOptionPane.YES_OPTION) {
                if (kiemtratrung()) {
                    if (bus.changecauhoi(cauhoi2)) {
//                        CauHoiDTO cauhoi1 = bus.readcauhoi().set(i, cauhoi2);
                        model1.setValueAt(cauhoi2.getCauHoi(), i, 0);
                        model1.setValueAt(cauhoi2.getCauDung(), i, 1);
                        model1.setValueAt(cauhoi2.getCauSai1(), i, 2);
                        model1.setValueAt(cauhoi2.getCauSai2(), i, 3);
                        model1.setValueAt(cauhoi2.getCauSai3(), i, 4);
                        reset1();
                        JOptionPane.showMessageDialog(rootPane, "Sửa Thành Công");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sửa Thất Bại");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Dữ liệu không được trùng nhau");
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Sửa Thất Bại1");
            }
        }

    }//GEN-LAST:event_btnsuaActionPerformed

    public void showResult() {
        CauHoiDTO cau = bus.dsch.get(bus.dsch.size() - 1);
        model1.addRow(new Object[]{
            cau.getCauHoi(), cau.getCauDung(), cau.getCauSai1(), cau.getCauSai2(), cau.getCauSai3()
        });
    }

    public void reset() {

        txtcore.setText("");
        txttime.setText("");
        txtquestion.setText("");
        txtnum.setText("");

    }

    public void reset1() {
        txtquestion.setText("");
        txttrue.setText("");
        false1.setText("");
        false2.setText("");
        false3.setText("");
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
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btnmodify;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsua;
    private javax.swing.JTextField false1;
    private javax.swing.JTextField false2;
    private javax.swing.JTextField false3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tb1;
    private javax.swing.JTable tb2;
    private javax.swing.JTextField txtcore;
    private javax.swing.JTextField txtnum;
    private javax.swing.JTextField txtquestion;
    private javax.swing.JTextField txttime;
    private javax.swing.JTextField txttrue;
    // End of variables declaration//GEN-END:variables
}
