// File: Habit.java
import java.sql.Timestamp;
public class Habit {
    private int habitId;
    private int userId;
    private String habitName;
    private String description;
    private Timestamp createdAt;
    private String frequency;
    private String status;


    public Habit(int habitId, int userId, String habitName, String description, String frequency, String status, Timestamp createdAt) {
        this.habitId = habitId;
        this.userId = userId;
        this.habitName = habitName;
        this.description = description;
        this.frequency = frequency;
        this.status = status;
        this.createdAt = createdAt;
    }
    // Constructor without id (used for inserting new habits)
    public Habit(int userId, String habitName, String description, String frequency, String status) {
        this.userId = userId;
        this.habitName = habitName;
        this.description = description;
        this.frequency = frequency;
        this.status = status;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }


    // Getters and setters
    public int getHabitId() { return habitId; }
    public void setHabitId(int habitId) { this.habitId = habitId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId;}

    public String getHabitName() { return habitName; }
    public void setHabitName(String habitName) { this.habitName = habitName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt(){return createdAt;}
    public void setCreatedAt(Timestamp createdAt){this.createdAt = createdAt;}

    @Override
    public String toString() {
        return "\n📌 Habit ID: " + habitId +
                "\n👤 User ID: " + userId +
                "\n🏷️ Name: " + habitName +
                "\n📝 Description: " + description +
                "\n🔁 Frequency: " + frequency +
                "\n📅 Created At: " + createdAt +
                "\n📊 Status: " + status + "\n";
    }
}
