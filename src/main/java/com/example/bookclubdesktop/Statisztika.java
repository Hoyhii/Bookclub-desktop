package com.example.bookclubdesktop;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Statisztika {
    static List<Member> members;
    public static void main(String[] args) {
        try {
            Beolvas();
            if (members.isEmpty()){
                throw new Exception("Nincs tag az adatbázisban!");
            }
            bannedPeople();
            youngerThan18();
            oldestBirthday();
            membersByGender();
        } catch (SQLException e) {
            System.out.println("Hiba történt az adatbázis kapcsolat kialakításakor.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void membersByGender() {
        //
    }

    private static void oldestBirthday() {
        //
    }

    private static void youngerThan18() {
        int db = 0;
        for (Member member: members
             ) {
            if (member.getBirth_date().isAfter(LocalDate.now().minusYears(18))){
                db++;
            }
        }
        if (db > 0) {
            System.out.println("Van a tagok között 18 évnél fiatalabb személy.");
        }else {
            System.out.println("Nincs a tagok között 18évnél fiatalabb személy.");
        }
    }

    private static void bannedPeople() {
        long count = members.stream().filter(member -> member.isBanned()).count();
        System.out.println("Kitiltott tagok száma: "+count);
    }



    private static void Beolvas() throws SQLException {
        BookclubDB db = new BookclubDB();
        members = db.getMembers();
    }
}
