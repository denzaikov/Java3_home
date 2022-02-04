package ru.geekbrains.march.chat.server;

import java.sql.*;

public class JdbcAuth implements AuthenticationProvider {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;

    public static void main(String[] args) {

        try {
            connect();
            System.out.println(stmt.executeUpdate("select * from users ;"));
            stmt.executeUpdate("INSERT INTO users (login, password, nickname) VALUES ('den', 123, 'superden')");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

//    public static void add() throws SQLException {
//        psInsert = connection.prepareStatement("INSERT INTO users (login, password, nickname) VALUES  (?, ?, ?);");
//        psInsert.setString(1, "denis");
//        psInsert.setString(2, "123asd");
//        psInsert.setString(3, "superden");
//        psInsert.executeUpdate();
//    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {

        try (ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE login = '" + login + "'AND password = '" + password + "' ;")) {  //
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeNickname(String oldNickname, String newNickname) {

        try {
            stmt.executeUpdate("UPDATE users SET nickname = '"+ newNickname +"' WHERE nickname = '" + oldNickname + "';");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:auth.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Невозможно подключиться к БД");
        }
    }

    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (psInsert != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
