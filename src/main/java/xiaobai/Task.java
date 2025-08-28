package xiaobai;

/**
 * A generic task with description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
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
        return getStatusIcon() + " " + description;
    }
}

