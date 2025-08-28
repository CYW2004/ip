package xiaobai;

/**
 * Represents a todo task with only a description.
 */
public class Todo extends Task {
    /**
     * Creates a Todo task with the given description.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     *
     * @return Formatted string representation of the todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

