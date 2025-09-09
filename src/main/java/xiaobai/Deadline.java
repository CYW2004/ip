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
        assert description != null : "Description must not be null";
        assert byRaw != null : "Raw deadline must not be null";
        this.by = DateTimeUtil.parseDateTimeLenient(byRaw);
        assert by != null : "Parsed deadline must not be null";
    }

    /**
     * Creates a Deadline task with the given description and LocalDateTime deadline.
     *
     * @param description Description of the task.
     * @param by Deadline of the task as LocalDateTime.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        assert description != null : "Description must not be null";
        assert by != null : "Deadline must not be null";
        this.by = by;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return Deadline as LocalDateTime.
     */
    public LocalDateTime getBy() {
        assert by != null : "Deadline must not be null";
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
        assert by != null : "Deadline must not be null before printing";
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.print(by) + ")";
    }
}
