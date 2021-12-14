package Server.DAO;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Connect {

    private final String host;
    private final String user;
    private final String psswd;
    private final String database;
    private Connection con;
    private PreparedStatement ps = null;
    private Statement st = null;
    private ResultSet rs = null;

    public Connect() {
        this.host = "localhost";
        this.user = "sa";
        this.psswd = "ngan2000";
        this.database = "hoc_vien_momo";
    }

    protected Connection getConnection() {
        if (this.con == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection("jdbc:sqlserver://" + this.host + ":1433;databasename=" + this.database + ";username=" + this.user + ";password=" + this.psswd + "");
                // System.out.println(con);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu");
            }
        }
        return con;
    }

    public PreparedStatement getPreparedStatement(String sql) throws Exception {
        if (this.ps == null ? true : this.ps.isClosed()) {
            this.ps = getConnection().prepareStatement(sql);
        }
        return this.ps;
    }

    public ResultSet executeQuery() throws Exception {
        try {
            this.rs = this.ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi lấy cơ sở dữ liệu");
        }

        return rs;
    }

    public boolean excuteUpdate() throws Exception {
        boolean kq = this.ps.executeUpdate() > 0;
        close();
        return kq;
    }

    public void close() throws Exception {
        if (this.rs != null && !this.rs.isClosed()) {
            this.rs.close();
            this.rs = null;
        }
        if (this.ps != null && !this.ps.isClosed()) {
            this.ps.close();
            this.ps = null;
        }
        if (this.con != null && !this.con.isClosed()) {
            this.con.close();
            this.con = null;
        }
    }

    // test connect db
    public static void main(String[] args) {
        new Connect().getConnection();
    }
}
