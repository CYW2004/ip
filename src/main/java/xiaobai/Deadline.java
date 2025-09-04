package xiaobai;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a Deadline task with the given description and raw deadline string.
     *
     * @param description Description of the task.
     * @param byRaw Deadline of the task in string format.
     */
    public Deadline(String description, String byRaw) throws XiaoBaiException {
        super(description);
        this.by = DateTimeUtil.parseDateTimeLenient(byRaw);
    }

    /**
     * Creates a Deadline task with the given description and LocalDateTime deadline.
     *
     * @param description Description of the task.
     * @param by Deadline of the task as LocalDateTime.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return Deadline as LocalDateTime.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task,
     * including its type, description, and due date.
     *
     * @return Formatted string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.print(by) + ")";
    }
}

