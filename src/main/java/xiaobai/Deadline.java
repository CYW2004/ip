package xiaobai;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String description, String byRaw) {
        super(description);
        this.by = DateTimeUtil.parseDateTimeLenient(byRaw);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.print(by) + ")";
    }
}

