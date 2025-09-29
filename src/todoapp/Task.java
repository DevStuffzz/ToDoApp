package todoapp;

/**
 * @author Corey Beaver
 * 
 * Task contains data for the task itself
 */
public class Task {

    private String name;       // The name of the task
    private String file_name;  // Snake case version (for file saving if needed)
    private boolean completed = false;

    public Task(String taskName) {
        this.name = taskName;
        this.file_name = toSnakeCase(taskName);
    }

    /**
     * Converts a String into snake_case.
     */
    private String toSnakeCase(String s) {
        return s.toLowerCase().replaceAll(" ", "_");
    }

    // Public Getters/Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.file_name = toSnakeCase(name);
    }

    public String getFile_name() {
        return file_name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Pretty printing for console.
     * Example:
     *   [✓] Do dishes
     *   [✗] Clean room
     */
    @Override
    public String toString() {
        return (completed ? "[✓] " : "[✗] ") + name;
    }
}
