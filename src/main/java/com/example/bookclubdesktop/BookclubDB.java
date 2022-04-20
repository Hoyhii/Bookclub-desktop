package com.example.bookclubdesktop;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookclubDB {
    Connection conn;
    String DB_DRIVER = "mysql";
    String DB_HOST = "Localhost";
    String DB_PORT = "3306";
    String DB_DATABASE = "klub";
    String DB_USER = "root";
    String DB_PASS = "";
    public BookclubDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s",DB_DRIVER,DB_HOST,DB_PORT,DB_DATABASE);
        conn = DriverManager.getConnection(url,DB_USER,DB_PASS);
    }

    public List<Member> getMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String gender = resultSet.getString("gender");
            LocalDate birth_date = resultSet.getDate("birth_date").toLocalDate();
            boolean banned = resultSet.getBoolean("banned");
            Member member = new Member(id, name, gender, birth_date, banned);
            members.add(member);
        }

        return members;

    }
    public boolean bannable(Member bannable) throws SQLException {  //rossz csak próbálkozás
        if (getBannedMember(bannable) == 1){
            String sql = "UPDATE members SET banned = 1 WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBoolean(1, bannable.isBanned());
            return statement.executeUpdate() > 0;
        }else{
            String sql = "UPDATE members SET banned = 0 WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBoolean(1, bannable.isBanned());
            return statement.executeUpdate() > 0;
        }

    }
    private long getBannedMember(Member member) throws SQLException { //rossz
        String sql = "SELECT banned FROM members WHERE id = ? ";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, member.getId());
        ResultSet resultSet = statement.executeQuery();
        int db = 0;
        if (resultSet.next()){
            db = resultSet.getInt("banned");
        }
        return db;
    }
}