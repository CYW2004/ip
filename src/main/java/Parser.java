public class Parser {

    public static Command parse(String input) throws XiaoBaiException {
        String s = input.trim();
        if (s.isEmpty()) {
            throw new UnknownCommandException(input);
        }

        if (s.equals("bye")) return new ByeCommand();
        if (s.equals("list")) return new ListCommand();

        if (s.startsWith("todo")) {
            String rest = s.length() > 4 ? s.substring(5) : "";
            if (rest.isBlank()) {
                throw new EmptyDescriptionException("todo");
            }
            return new AddTodoCommand(rest);
        }

        if (s.startsWith("deadline")) {
            String rest = s.length() > 8 ? s.substring(9) : "";
            String[] parts = rest.split("\\s+/by\\s+", 2);
            if (parts.length < 2) {
                throw new InvalidFormatException("Use: deadline <description> /by <due>");
            }
            String desc = parts[0].trim();
            String due  = parts[1].trim();
            if (desc.isBlank()) {
                throw new EmptyDescriptionException("deadline");
            }
            if (due.isBlank()) {
                throw new InvalidFormatException("Use: deadline <description> /by <due>");
            }
            return new AddDeadlineCommand(desc, due);
        }

        if (s.startsWith("event")) {
            String rest = s.length() > 5 ? s.substring(6) : "";
            String[] p1 = rest.split("\\s+/from\\s+", 2);
            if (p1.length < 2) {
                throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
            }
            String desc = p1[0].trim();
            String[] p2 = p1[1].split("\\s+/to\\s+", 2);
            if (p2.length < 2) {
                throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
            }
            String start = p2[0].trim();
            String end   = p2[1].trim();

            if (desc.isBlank()) {
                throw new EmptyDescriptionException("event");
            }
            if (start.isBlank() || end.isBlank()) {
                throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
            }
            return new AddEventCommand(desc, start, end);
        }

        if (s.startsWith("mark ")) {
            int idx = parseIndexOrThrow(s.substring(5), "mark");
            return new MarkCommand(idx);
        }

        if (s.startsWith("unmark ")) {
            int idx = parseIndexOrThrow(s.substring(7), "unmark");
            return new UnmarkCommand(idx);
        }

        if (s.startsWith("delete ")) {
            int idx = parseIndexOrThrow(s.substring(7), "delete");
            return new DeleteCommand(idx);
        }

        if (s.startsWith("on ")) {
            String raw = s.substring(3).trim();
            if (raw.isBlank()) {
                throw new InvalidFormatException("Use: on <yyyy-MM-dd|d/M/yyyy>");
            }
            return new OnCommand(raw);
        }

        throw new UnknownCommandException(s);
    }

    private static int parseIndexOrThrow(String s, String cmdName) throws XiaoBaiException {
        String t = s.trim();
        if (t.isEmpty()) {
            throw new InvalidFormatException("Use: " + cmdName + " <task number>");
        }
        try {
            int idx = Integer.parseInt(t);
            if (idx <= 0) {
                throw new InvalidIndexException();
            }
            return idx;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Use: " + cmdName + " <task number>");
        }
    }
}
