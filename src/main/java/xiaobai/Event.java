package xiaobai;

import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates an Event task with description, start and end times.
     *
     * @param description Task description.
     * @param startRaw Start time.
     * @param endRaw End time.
     */
    public Event(String description, String startRaw, String endRaw) {
        super(description);
        this.start = DateTimeUtil.parseDateTimeLenient(startRaw);
        this.end = DateTimeUtil.parseDateTimeLenient(endRaw);
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Event end time cannot be before start time");
        }
    }

    /**
     * Creates an Event task with description, start and end times.
     *
     * @param description Task description.
     * @param start Start time.
     * @param end End time.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Event end time cannot be before start time");
        }
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return Formatted string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.print(start)
                + " to: " + DateTimeUtil.print(end) + ")";
    }
}

