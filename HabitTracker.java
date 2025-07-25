// File: HabitTracker.java
import java.util.List;
import java.util.Scanner;

public class HabitTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HabitService habitService = new HabitService();

        while (true) {
            System.out.println("\n== HabitMate Menu ==");
            System.out.println("1. Add Habit");
            System.out.println("2. View Habits");
            System.out.println("3. Delete Habit");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter user ID:");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter habit name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter frequency (daily/weekly): ");
                    String freq = scanner.nextLine();
                    System.out.print("Enter status (pending/completed): ");
                    String status = scanner.nextLine();

                    Habit habit = new Habit(userId, name, desc, freq, status);
                    habitService.addHabit(habit);
                    break;

                case 2:
                    List<Habit> habits = habitService.getAllHabits();
                    for (Habit h : habits) {
                        System.out.println(h);
                    }
                    break;

                case 3:
                    System.out.print("Enter Habit ID to delete: ");
                    int habitId = scanner.nextInt();
                    habitService.deleteHabit(habitId);
                    break;

                case 4:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

