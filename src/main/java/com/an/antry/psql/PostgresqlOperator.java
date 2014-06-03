package com.an.antry.psql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlOperator {
    private Connection conn;

    public PostgresqlOperator() {
        try {
            init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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
