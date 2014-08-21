package com.an.antry.mssql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MssqlMain {
    private Connection conn;

    public static void main(String[] args) {
        run();
    }

    public MssqlMain() {
    }

    private static void run() {
        String user = "ShunhuiZ";
        String passwd = "J8TF3Dd@s";

        String driver = "net.sourceforge.jtds.jdbc.Driver";
        String url = "JDBC:jtds:sqlserver://10.50.128.127:1433/DEA";

        String driver2 = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        String url2 = "jdbc:microsoft:sqlserver//10.50.128.127:1433";
        String database = "DEA";
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.currentTimeMillis();

        String sqlAll = "SELECT TOP 100 serialnumber , product_id , firmware_string, wan_ip , locale, modifieddate,createddate FROM dearegistration;";
        String sqlCount = "SELECT count(*) FROM dearegistration WHERE modifieddate > '2014-08-15 00:00:00';";
        String sqlModified = "SELECT serialnumber , product_id , firmware_string, wan_ip , locale, modifieddate,createddate FROM dearegistration WHERE modifieddate > '2014-08-15 00:00:00' ORDER BY modifieddate;";

        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url, user, passwd);
            if (con.isClosed()) {
                System.out.println("Connection is closed.");
                return;
            }
            smt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // 建立描述，设定结果集支持滚动光标且敏感，不可编辑
            rs = smt.executeQuery(sqlModified);
            int i = 0;
            while (rs.next()) {
                // int count = rs.getInt(1);
                // System.out.println(count);
                String sn = rs.getString("serialnumber");
                int pid = rs.getInt("product_id");
                String firmware = rs.getString("firmware_string");
                String wanIp = rs.getString("wan_ip");
                String locale = rs.getString("locale");
                String modifiedDate = rs.getString("modifieddate");
                String createdDate = rs.getString("createddate");
                System.out.println(i++ + ", " + sn + ", " + pid + ", " + firmware + ", " + wanIp + ", " + locale + ", "
                        + modifiedDate + ", " + createdDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                smt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() throws ClassNotFoundException, SQLException {
        System.out.println("Initialize PostgreSQL connection.");
        Class.forName("org.postgresql.Driver");
        String host = "10.203.15.101";
        String dbname = "suss";
        String url = String.format("jdbc:postgresql://%s:5432/%s", host, dbname);
        String username = "postgres";
        String password = "postgres";
        System.out.println(String.format("Init connection, url: %s, username: %s, password: %s", url, username,
                password));
        conn = DriverManager.getConnection(url, username, password);
        System.out.println("conn: " + conn);
        conn.close();
    }
}
