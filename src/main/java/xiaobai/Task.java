package xiaobai;

/**
 * A generic task with description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        assert description != null : "Description must not be null";
        this.description = description;
        this.isDone = false;
        assert !isDone : "New task must be not done by default";
    }

    public void markAsDone() {
        isDone = true;
        assert isDone : "Task should be marked as done";
    }

    public void markAsNotDone() {
        isDone = false;
        assert !isDone : "Task should be marked as not done";
    }

    /**
     * Returns the task status icon.
     * "[X]" if done, "[ ]" if not done.
     */
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns a string representation of the task,
     * including its status icon and description.
     *
     * @return Formatted string representation of the task.
     */
    @Override
    public String toString() {
        assert description != null : "Task description must not be null";
        return getStatusIcon() + " " + description;
    }
}
