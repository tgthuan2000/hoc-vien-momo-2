/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServerHocVienMomo.ConnectDB;

import java.awt.List;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author NGAN DOAN
 */
public class sqlconnect {
    private String host=" ";
    private String user="sa";
    private String psswd="ngan2000";
    private String database=" ";
    private Connection con;
    private PreparedStatement ps=null;
    private Statement st= null;
    private ResultSet rs=null;
    
    public sqlconnect(){}

    public sqlconnect(String host,String user,String psswd,String database) {
        this.host=host;
        this.user=user;
        this.psswd=psswd;
        this.database=database;
    }
    
   
    
    protected Connection getConnection(){
        if(this.con==null){
           
            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con= DriverManager.getConnection("jdbc:sqlserver://"+this.host+":1433;databaseName="+this.database+";username="+this.user+";password="+this.psswd+"");
                System.out.println(con);
//                System.out.println("lala");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu");
                e.printStackTrace();
            }
        }
        
        return con;
    }
    
    
    public PreparedStatement getPreparedStatement(String sql) throws Exception{
        if(this.ps == null ? true:this.ps.isClosed()){
            this.ps=getConnection().prepareStatement(sql);
        }
        return this.ps;
    }
    
    public ResultSet executeQuery() throws Exception{
        try {
              this.rs= this.ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi lấy cơ sở dữ liệu");
        }
      
        return rs;
    }
    
    public boolean excuteUpdate() throws Exception{
        return this.ps.executeUpdate()>0;
    }
    
    

   public void close() throws Exception{
       if(this.rs != null && !this.rs.isClosed()){
           this.rs.close();
           this.rs = null;
       }
       if(this.ps != null && !this.ps.isClosed()){
           this.ps.close();
           this.ps = null;
       }
       if(this.con != null && !this.con.isClosed()){
           this.con.close();
           this.con = null;
       }
    }
//    public static void main(String[] args) {
//        sqlconnect sql =new sqlconnect("localhost","sa", "ngan2000", "hocvienmomo");
//        sql.getConnection();
//    }
}
