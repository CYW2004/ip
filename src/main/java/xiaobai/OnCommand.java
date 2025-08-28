package xiaobai;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OnCommand extends Command {
    private final String raw;

    /**
     * Creates an OnCommand with the given raw date string.
     *
     * @param raw Raw date string.
     */
    public OnCommand(String raw) { this.raw = raw; }

    /**
     * Finds all deadlines and events that fall on the given date,
     * and prints them to the user.
     * Supports date formats yyyy-MM-dd and d/M/yyyy.
     *
     * @param tasks Task list.
     * @param ui User interface.
     * @param storage Storage handler
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws XiaoBaiException {
        LocalDate d;
        try {
            d = LocalDate.parse(raw.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            try {
                d = LocalDate.parse(raw.trim(), DateTimeFormatter.ofPattern("d/M/uuuu"));
            } catch (Exception ex) {
                throw new XiaoBaiException("Please give a date as yyyy-MM-dd or d/M/yyyy, e.g., 2019-12-02");
            }
        }
        StringBuilder sb = new StringBuilder();
        int n = 0;
        for (Task t : tasks.asList()) {
            boolean match = false;
            if (t instanceof Deadline) {
                match = ((Deadline) t).getBy().toLocalDate().equals(d);
            } else if (t instanceof Event) {
                Event e = (Event) t;
                match = !e.getEnd().toLocalDate().isBefore(d) && !e.getStart().toLocalDate().isAfter(d);
            }
            if (match) {
                sb.append(++n).append(". ").append(t).append("\n");
            }
        }
        if (n == 0) {
            ui.printBoxed("No deadlines/events on " + DateTimeUtil.print(d));
        } else {
            ui.printBoxed("On " + DateTimeUtil.print(d) + ":\n" + sb.toString().trim());
        }
    }
}
