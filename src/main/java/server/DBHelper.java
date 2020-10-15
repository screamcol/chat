package server;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private Statement stmt;
    private Connection conn;


    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(JDBC.PREFIX + "DB.db");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insert(int id, String username, String password, String nickname) {
        String insertQuery = String.format("insert into Users values(%d, '%s', '%s', '%s');", id, username, password, nickname);
        try {
            stmt.execute(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        String createTable = "CREATE TABLE IF NOT EXISTS Users(id INTEGER, Username TEXT, Password TEXT, Nickname TEXT, PRIMARY KEY (id));";
        try {
            stmt.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNickname(String oldNickname, String newNickname) {
        String updateNick = String.format("UPDATE Users SET Nickname = '%s' WHERE Nickname = '%s';", newNickname, oldNickname);
        try {
            stmt.execute(updateNick);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectNickname(String username, String password) {
        String query = String.format("select Nickname from Users where Username = '%s' and Password = '%s';", username, password);
        ResultSet rs;
        String result = null;
        try {
            rs = stmt.executeQuery(query);
            result = rs.getString("Nickname");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> selectAll() {
        ArrayList<String> userName = new ArrayList<>();
        try {
            String selectAllQuery = "SELECT * FROM Users;";
            ResultSet rs = stmt.executeQuery(selectAllQuery);
            while (rs.next()) {
                String username = rs.getString("Username");
                userName.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userName;
    }

    public List<String> selectAllNicknames(String nickname) {
        List<String> usernames = new ArrayList<>();
        try {
            String selectNick = String.format("SELECT * FROM Users WHERE Nickname = '%s';", nickname);
            ResultSet rs = stmt.executeQuery(selectNick);
            while (rs.next()) {
                String username = rs.getString("Nickname");
                usernames.add(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usernames;
    }


}
