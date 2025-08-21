import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    public static void listTasks(List<Task> tasks, Ui ui) {
        if (tasks.isEmpty()) {
            ui.printBoxed("Your task list is empty.");
            return;
        }
        String header = "Here are the tasks in your list:";
        List<String> lines = new ArrayList<>();
        lines.add(header);
        for (int i = 0; i < tasks.size(); i++) {
            lines.add((i + 1) + "." + tasks.get(i).toString());
        }
        ui.printBoxed(lines.toArray(new String[0]));
    }

    public static void addTodo(List<Task> tasks, String rest, Ui ui) throws XiaoBaiException {
        String desc = rest.trim();
        if (desc.isEmpty()) throw new EmptyDescriptionException("todo");
        Task t = new Todo(desc);
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:", "  " + t, "Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void addDeadline(List<Task> tasks, String rest, Ui ui) throws XiaoBaiException {
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("Use: deadline <description> /by <due>");
        }
        Task t = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:", "  " + t, "Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void addEvent(List<Task> tasks, String rest, Ui ui) throws XiaoBaiException {
        String[] p1 = rest.split(" /from ", 2);
        if (p1.length < 2 || p1[0].trim().isEmpty()) {
            throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
        }
        String[] p2 = p1[1].split(" /to ", 2);
        if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
            throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
        }
        Task t = new Event(p1[0].trim(), p2[0].trim(), p2[1].trim());
        tasks.add(t);
        ui.printBoxed("Got it. I've added this task:", "  " + t, "Now you have " + tasks.size() + " tasks in the list.");
    }

    public static void markTask(List<Task> tasks, String rest, boolean done, Ui ui) throws XiaoBaiException {
        int idx = Parser.parseIndexOrThrow(rest, (done ? "mark" : "unmark") + " <task number>");
        Parser.ensureIndexInRange(idx, tasks.size());
        Task t = tasks.get(idx);
        if (done) t.markAsDone(); else t.markAsNotDone();
        ui.printBoxed(
                done ? "Nice! I've marked this task as done:" : "OK, I've marked this task as not done yet:",
                "  " + t
        );
    }

    public static void deleteTask(List<Task> tasks, String rest, Ui ui) throws XiaoBaiException {
        int idx = Parser.parseIndexOrThrow(rest, "delete <task number>");
        Parser.ensureIndexInRange(idx, tasks.size());
        Task removed = tasks.remove(idx);
        ui.printBoxed(
                "Noted. I've removed this task:",
                "  " + removed,
                "Now you have " + tasks.size() + " tasks in the list."
        );
    }
}
