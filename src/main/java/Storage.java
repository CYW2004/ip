import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path FILE = Paths.get("data", "xiaobai.txt");

    /** Load tasks from disk. If the doesn't exist, returns an empty list. */
    public List<Task> load(Ui ui) {
        List<Task> tasks = new ArrayList<>();

        try {
            if (FILE.getParent() != null) {
                Files.createDirectories(FILE.getParent());
            }
            if (!Files.exists(FILE)) {
                return tasks;
            }

            int corrupted = 0;
            try (BufferedReader br = Files.newBufferedReader(FILE, StandardCharsets.UTF_8)) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    Task t = parseLine(line);
                    if (t != null) {
                        tasks.add(t);
                    } else {
                        corrupted++;
                    }
                }
            }

            if (corrupted > 0 && ui != null) {
                ui.printBoxed("(・ω・)ﾉ Some saved lines were corrupted and were skipped: " + corrupted);
            }

        } catch (IOException e) {
            if (ui != null) {
                ui.printErrorBox("(>_<) Failed to load tasks: " + e.getMessage());
            }
        }

        return tasks;
    }

    /** Save tasks to disk */
    public void save(List<Task> tasks, Ui ui) {
        try {
            if (FILE.getParent() != null) {
                Files.createDirectories(FILE.getParent());
            }
            try (BufferedWriter bw = Files.newBufferedWriter(FILE, StandardCharsets.UTF_8)) {
                for (Task t : tasks) {
                    bw.write(serialize(t));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            if (ui != null) {
                ui.printErrorBox("(>_<) Failed to save tasks: " + e.getMessage());
            }
        }
    }

    // Helpers:

    private Task parseLine(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        try {
            if (parts.length >= 3) {
                String type = parts[0].trim();
                String doneStr = parts[1].trim();
                String desc = parts[2];

                boolean isDone = doneStr.equals("1") || doneStr.equalsIgnoreCase("true") || doneStr.equalsIgnoreCase("x");

                Task t;
                switch (type) {
                    case "T":
                        t = new Todo(desc);
                        break;
                    case "D":
                        if (parts.length < 4) return null;
                        t = new Deadline(desc, parts[3]);
                        break;
                    case "E":
                        if (parts.length < 5) return null;
                        t = new Event(desc, parts[3], parts[4]);
                        break;
                    default:
                        t = null;
                }
                if (t != null && isDone) t.markAsDone();
                return t;
            }
        } catch (Exception ignore) {
        }

        try {
            if (!line.startsWith("[")) return null;
            char type = line.charAt(1);
            int secondBracket = line.indexOf("][", 2);
            if (secondBracket < 0 || secondBracket + 2 >= line.length()) return null;
            char doneChar = line.charAt(secondBracket + 2);
            boolean isDone = (doneChar == 'X' || doneChar == 'x');

            int afterStatus = line.indexOf("] ", secondBracket + 1);
            if (afterStatus < 0) return null;
            String rest = line.substring(afterStatus + 2);

            Task t;
            switch (type) {
                case 'T': {
                    t = new Todo(rest);
                    break;
                }
                case 'D': {
                    int byIdx = rest.lastIndexOf("(by:");
                    if (byIdx < 0) return null;
                    String desc = rest.substring(0, byIdx).trim();
                    String by = rest.substring(byIdx + 4).trim();
                    if (by.endsWith(")")) by = by.substring(0, by.length() - 1).trim();
                    t = new Deadline(desc, by);
                    break;
                }
                case 'E': {
                    // "...desc (from: START to: END)"
                    int fromIdx = rest.lastIndexOf("(from:");
                    int toIdx = rest.lastIndexOf(" to:");
                    if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) return null;
                    String desc = rest.substring(0, fromIdx).trim();
                    String from = rest.substring(fromIdx + 6, toIdx).trim();
                    String to = rest.substring(toIdx + 4).trim();
                    if (to.endsWith(")")) to = to.substring(0, to.length() - 1).trim();
                    t = new Event(desc, from, to);
                    break;
                }
                default:
                    return null;
            }
            if (isDone) t.markAsDone();
            return t;

        } catch (Exception ignore) {
            return null; // corrupted
        }
    }

    private String serialize(Task t) {
        boolean isDone = t.getStatusIcon().contains("X"); // uses existing API
        String done = isDone ? "1" : "0";

        if (t instanceof Todo) {
            String desc = stripPrefix(t.toString(), "[T]").trim();
            desc = stripStatus(desc);
            return String.join(" | ", "T", done, desc);
        } else if (t instanceof Deadline) {
            String s = stripPrefix(t.toString(), "[D]").trim();
            String desc = stripStatus(s);
            String by = extractSuffix(desc, "(by:");
            desc = removeSuffix(desc, "(by:");
            return String.join(" | ", "D", done, desc, by);
        } else if (t instanceof Event) {
            String s = stripPrefix(t.toString(), "[E]").trim();
            String desc = stripStatus(s);
            String start = extractSuffix(desc, "(from:");
            String end = "";
            int toIdx = start.lastIndexOf(" to:");
            if (toIdx >= 0) {
                end = start.substring(toIdx + 4).trim();
                start = start.substring(0, toIdx).trim();
                if (end.endsWith(")")) end = end.substring(0, end.length() - 1).trim();
            }
            desc = removeSuffix(desc, "(from:");
            return String.join(" | ", "E", done, desc, start, end);
        } else {
            return "T | " + done + " | " + t.toString();
        }
    }

    private String stripPrefix(String s, String prefix) {
        return s.startsWith(prefix) ? s.substring(prefix.length()) : s;
    }

    private String stripStatus(String s) {
        if (s.startsWith("[X] ")) return s.substring(4);
        if (s.startsWith("[ ] ")) return s.substring(4);
        return s;
    }

    private String extractSuffix(String s, String marker) {
        int idx = s.lastIndexOf(marker);
        if (idx < 0) return "";
        String tail = s.substring(idx + marker.length()).trim();
        if (tail.endsWith(")")) tail = tail.substring(0, tail.length() - 1).trim();
        return tail;
    }

    private String removeSuffix(String s, String marker) {
        int idx = s.lastIndexOf(marker);
        if (idx < 0) return s;
        return s.substring(0, idx).trim();
    }
}
