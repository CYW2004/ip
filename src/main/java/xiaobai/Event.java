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
    public Event(String description, String startRaw, String endRaw) throws XiaoBaiException {
        super(description);
        assert description != null : "Description must not be null";
        assert startRaw != null : "Raw start time must not be null";
        assert endRaw != null : "Raw end time must not be null";
        this.start = DateTimeUtil.parseDateTimeLenient(startRaw);
        this.end = DateTimeUtil.parseDateTimeLenient(endRaw);
        assert start != null : "Parsed start time must not be null";
        assert end != null : "Parsed end time must not be null";
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
        assert description != null : "Description must not be null";
        assert start != null : "Start time must not be null";
        assert end != null : "End time must not be null";
        this.start = start;
        this.end = end;
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Event end time cannot be before start time");
        }
    }

    public LocalDateTime getStart() {
        assert start != null : "Start time must not be null";
        return start;
    }

    public LocalDateTime getEnd() {
        assert end != null : "End time must not be null";
        return end;
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return Formatted string representation of the event task.
     */
    @Override
    public String toString() {
        assert start != null : "Start time must not be null before printing";
        assert end != null : "End time must not be null before printing";
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.print(start)
                + " to: " + DateTimeUtil.print(end) + ")";
    }
}
