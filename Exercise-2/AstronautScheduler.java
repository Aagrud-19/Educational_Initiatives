import java.util.*;
import java.util.logging.*;
import java.time.*;
import java.time.format.*;

// ===================== Task Class =====================
// Represents a single task with description, start/end times, priority, and completion status
// No design pattern here; standard POJO (Plain Old Java Object)
class Task {
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Priority priority;
    private boolean completed;

    // Enum for task priority
    public enum Priority { HIGH, MEDIUM, LOW }

    // Constructor
    public Task(String description, LocalTime startTime, LocalTime endTime, Priority priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.completed = false;
    }

    // Getter and setter methods
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    // toString method for displaying tasks in the console
    @Override
    public String toString() {
        String base = String.format("%s - %s: %s [%s]",
                startTime, endTime, description, priority);
        if (completed) base += " (Completed)";
        return base;
    }
}

// ===================== Task Factory =====================
// Implements Factory Pattern to create Task objects
// Ensures proper parsing of time and priority validation
class TaskFactory {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static Task createTask(String description, String start, String end, String priorityStr) {
        try {
            // Parse start and end times
            LocalTime startTime = LocalTime.parse(start, TIME_FORMAT);
            LocalTime endTime = LocalTime.parse(end, TIME_FORMAT);

            // Validate end time is after start time
            if (!endTime.isAfter(startTime)) {
                throw new IllegalArgumentException("Error: End time must be after start time.");
            }

            // Parse priority
            Task.Priority priority = Task.Priority.valueOf(priorityStr.toUpperCase());
            return new Task(description, startTime, endTime, priority);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error: Invalid time format. Use HH:mm (00:00 - 23:59)");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: Invalid priority. Use High, Medium, or Low.");
        }
    }
}

// ===================== Observer Pattern =====================
// Interface for notifying conflicts
interface ConflictObserver {
    void notifyConflict(String conflictMessage);
}

// Console implementation of ConflictObserver
class ConsoleConflictObserver implements ConflictObserver {
    @Override
    public void notifyConflict(String conflictMessage) {
        System.out.println(conflictMessage);
    }
}

// ===================== Singleton Pattern =====================
// ScheduleManager manages all tasks and ensures only one instance exists (Singleton)
// Implements Observer Pattern for task conflict notifications
class ScheduleManager {
    private static ScheduleManager instance; // Singleton instance
    private final List<Task> tasks; // Stores all tasks
    private final List<ConflictObserver> observers; // Stores observers
    private static final Logger logger = Logger.getLogger(ScheduleManager.class.getName()); // Logging

    // Private constructor for Singleton
    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();

        // Initialize logger
        try {
            FileHandler handler = new FileHandler("astronaut_schedule.log", true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            System.out.println("Warning: Logging initialization failed.");
        }
    }

    // Singleton accessor
    public static synchronized ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }

    // Add observer for conflict notifications
    public void addObserver(ConflictObserver observer) { observers.add(observer); }

    // Notify all observers of conflicts
    private void notifyObservers(String message) {
        for (ConflictObserver obs : observers) obs.notifyConflict(message);
    }

    // Add a new task with conflict checking
    public boolean addTask(Task task) {
        for (Task t : tasks) {
            if (isConflict(task, t)) {
                String conflictMsg = String.format("Error: Task conflicts with existing task \"%s\".", t.getDescription());
                notifyObservers(conflictMsg);
                logger.warning(conflictMsg);
                return false;
            }
        }
        tasks.add(task);
        logger.info("Task added: " + task.getDescription());
        System.out.println("Task added successfully. No conflicts.");
        return true;
    }

    // Remove task by description
    public boolean removeTask(String description) {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getDescription().equalsIgnoreCase(description)) {
                it.remove();
                System.out.println("Task removed successfully.");
                logger.info("Task removed: " + description);
                return true;
            }
        }
        System.out.println("Error: Task not found.");
        logger.warning("Remove failed: " + description);
        return false;
    }

    // View all tasks sorted by start time
    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        tasks.sort(Comparator.comparing(Task::getStartTime));
        for (Task t : tasks) System.out.println(t);
    }

    // Edit a task by description
    public void editTask(String description, Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            if (t.getDescription().equalsIgnoreCase(description)) {
                tasks.set(i, updatedTask);
                System.out.println("Task updated successfully.");
                logger.info("Task updated: " + description);
                return;
            }
        }
        System.out.println("Error: Task not found.");
    }

    // Mark task as completed
    public void markTaskCompleted(String description) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                t.setCompleted(true);
                System.out.println("Task marked as completed.");
                logger.info("Task completed: " + description);
                return;
            }
        }
        System.out.println("Error: Task not found.");
    }

    // View tasks filtered by priority
    public void viewTasksByPriority(Task.Priority priority) {
        List<Task> filtered = new ArrayList<>();
        for (Task t : tasks) if (t.getPriority() == priority) filtered.add(t);

        if (filtered.isEmpty()) {
            System.out.println("No tasks with priority " + priority + ".");
            return;
        }
        filtered.sort(Comparator.comparing(Task::getStartTime));
        for (Task t : filtered) System.out.println(t);
    }

    // Check if two tasks conflict
    private boolean isConflict(Task a, Task b) {
        return !(a.getEndTime().isBefore(b.getStartTime()) || a.getStartTime().isAfter(b.getEndTime()));
    }
}

// ===================== Main Application =====================
// Provides console interface for astronauts to manage their schedule
public class AstronautScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduleManager manager = ScheduleManager.getInstance();
        manager.addObserver(new ConsoleConflictObserver()); // Observer for conflicts

        while (true) {
            // Display menu
            System.out.println("\n=== Astronaut Daily Schedule Organizer ===");
            System.out.println("1) Add Task");
            System.out.println("2) Remove Task (by description)");
            System.out.println("3) View All Tasks");
            System.out.println("4) Edit Task (by description)");
            System.out.println("5) Mark Task Completed (by description)");
            System.out.println("6) View Tasks by Priority");
            System.out.println("0) Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": // Add task
                    try {
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        System.out.print("Start time (HH:mm): ");
                        String start = sc.nextLine();
                        System.out.print("End time (HH:mm): ");
                        String end = sc.nextLine();
                        System.out.print("Priority (High/Medium/Low): ");
                        String priority = sc.nextLine();

                        Task newTask = TaskFactory.createTask(desc, start, end, priority);
                        manager.addTask(newTask);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2": // Remove task
                    System.out.print("Description of task to remove: ");
                    manager.removeTask(sc.nextLine());
                    break;

                case "3": // View all tasks
                    manager.viewTasks();
                    break;

                case "4": // Edit task
                    try {
                        System.out.print("Description of task to edit: ");
                        String oldDesc = sc.nextLine();
                        System.out.print("New description: ");
                        String newDesc = sc.nextLine();
                        System.out.print("New start time (HH:mm): ");
                        String newStart = sc.nextLine();
                        System.out.print("New end time (HH:mm): ");
                        String newEnd = sc.nextLine();
                        System.out.print("New priority (High/Medium/Low): ");
                        String newPriority = sc.nextLine();

                        Task updatedTask = TaskFactory.createTask(newDesc, newStart, newEnd, newPriority);
                        manager.editTask(oldDesc, updatedTask);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "5": // Mark completed
                    System.out.print("Description of task to mark completed: ");
                    manager.markTaskCompleted(sc.nextLine());
                    break;

                case "6": // View tasks by priority
                    System.out.print("Enter priority (High/Medium/Low): ");
                    try {
                        String priStr = sc.nextLine();
                        Task.Priority pri = Task.Priority.valueOf(priStr.toUpperCase());
                        manager.viewTasksByPriority(pri);
                    } catch (Exception e) {
                        System.out.println("Error: Invalid priority. Use High, Medium, or Low.");
                    }
                    break;

                case "0": // Exit
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
