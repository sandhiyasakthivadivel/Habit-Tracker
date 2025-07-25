// File: HabitService.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitService {
    private final Connection connection;

    public HabitService() {
        this.connection = DBConnect.getConnection();
    }

    // Add a new habit
    public void addHabit(Habit habit) {
        String sql = "INSERT INTO habits (user_id, habit_name, description, frequency, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, habit.getUserId());
            stmt.setString(2, habit.getHabitName());
            stmt.setString(3, habit.getDescription());
            stmt.setString(4, habit.getFrequency());
            stmt.setString(5, habit.getStatus());
            stmt.setTimestamp(6, habit.getCreatedAt());
            stmt.executeUpdate();
            System.out.println("✅ Habit added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding habit:");
            e.printStackTrace();
        }
    }

    // Fetch all habits
    public List<Habit> getAllHabits() {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM habits";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Habit habit = new Habit(
                        rs.getInt("habit_id"),
                        rs.getInt("user_id"),
                        rs.getString("habit_name"),
                        rs.getString("description"),
                        rs.getString("frequency"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
                habits.add(habit);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching habits:");
            e.printStackTrace();
        }
        return habits;
    }

    // Delete a habit by habit ID
    public void deleteHabit(int habitId) {
        String sql = "DELETE FROM habits WHERE habit_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("🗑️ Habit deleted successfully!");
            } else {
                System.out.println("❌ No habit found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting habit:");
            e.printStackTrace();
        }
    }
}

