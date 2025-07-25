// File: DBConnect.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = "jdbc:postgresql://localhost:5432/HabitMate";
    private static final String USER = "postgres"; // Replace with your PostgreSQL username
    private static final String PASSWORD = "Anvai@06"; // Replace with your PostgreSQL password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
