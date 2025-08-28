package xiaobai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading tasks to and from disk.
 * Uses text file serialization.
 */
public class Storage {
    private final Path FILE;

    /**
     * Creates a Storage instance with "data/xiaobai.txt".
     */
    public Storage() { // keep default behavior
        this(FILE_DEFAULT());
    }

    /**
     * Creates a Storage instance with the specified file path string.
     * Not useful for now.
     *
     * @param pathStr File path.
     */
    public Storage(String pathStr) {
        this(FILE_FROM(pathStr));
    }

    /**
     * Creates a Storage instance with the specified Path.
     * Still not useful for now.
     *
     * @param path Path object.
     */
    public Storage(Path path) {
        this.FILE = path;
    }

    private static Path FILE_DEFAULT() {
        return Paths.get("data", "xiaobai.txt");
    }

    private static Path FILE_FROM(String p) {
        return Paths.get(p);
    }


    /**
     * Loads tasks, returns an empty list if file does not exist.
     * Skips corrupted lines.
     *
     * @param ui User interface.
     * @return List of tasks loaded.
     */
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


    /**
     * Saves tasks to disk.
     * Creates parent directories if they do not exist.
     * Prints an error message if saving fails.
     *
     * @param tasks Task list.
     * @param ui User interface.
     */
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
                boolean isDone = parts[1].trim().equals("1");

                switch (type) {
                    case "T": {
                        String desc = parts[2];
                        Task t = new Todo(desc);
                        if (isDone) t.markAsDone();
                        return t;
                    }
                    case "D": {
                        if (parts.length < 4) return null;
                        String desc = parts[2];
                        String iso = parts[3];
                        Deadline d = new Deadline(desc, DateTimeUtil.fromIso(iso));
                        if (isDone) d.markAsDone();
                        return d;
                    }
                    case "E": {
                        if (parts.length < 5) return null;
                        String desc = parts[2];
                        String isoStart = parts[3];
                        String isoEnd = parts[4];
                        Event e = new Event(desc,
                                DateTimeUtil.fromIso(isoStart),
                                DateTimeUtil.fromIso(isoEnd));
                        if (isDone) e.markAsDone();
                        return e;
                    }
                    default:
                        return null;
                }
            }
        } catch (Exception ignore) { }

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
                    String byHuman = rest.substring(byIdx + 4).trim();
                    if (byHuman.endsWith(")")) byHuman = byHuman.substring(0, byHuman.length() - 1).trim();
                    t = new Deadline(desc, DateTimeUtil.parseDateTimeLenient(byHuman));
                    break;
                }
                case 'E': {
                    int fromIdx = rest.lastIndexOf("(from:");
                    int toIdx = rest.lastIndexOf(" to:");
                    if (fromIdx < 0 || toIdx < 0 || toIdx <= fromIdx) return null;
                    String desc = rest.substring(0, fromIdx).trim();
                    String startHuman = rest.substring(fromIdx + 6, toIdx).trim();
                    String endHuman = rest.substring(toIdx + 4).trim();
                    if (endHuman.endsWith(")")) endHuman = endHuman.substring(0, endHuman.length() - 1).trim();
                    t = new Event(desc,
                            DateTimeUtil.parseDateTimeLenient(startHuman),
                            DateTimeUtil.parseDateTimeLenient(endHuman));
                    break;
                }
                default:
                    return null;
            }
            if (isDone) t.markAsDone();
            return t;

        } catch (Exception ignore) {
            return null; // corrupted line
        }
    }

    private String serialize(Task t) {
        boolean isDone = t.getStatusIcon().contains("X");
        String done = isDone ? "1" : "0";

        if (t instanceof Todo) {
            String desc = stripStatus(stripPrefix(t.toString(), "[T]").trim());
            return String.join(" | ", "T", done, desc);
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            String desc = stripStatus(stripPrefix(d.toString(), "[D]").trim());
            String iso = DateTimeUtil.toIso(d.getBy());
            int idx = desc.lastIndexOf("(by:");
            if (idx >= 0) desc = desc.substring(0, idx).trim();
            return String.join(" | ", "D", done, desc, iso);
        } else if (t instanceof Event) {
            Event e = (Event) t;
            String desc = stripStatus(stripPrefix(e.toString(), "[E]").trim());
            String isoStart = DateTimeUtil.toIso(e.getStart());
            String isoEnd = DateTimeUtil.toIso(e.getEnd());
            int idx = desc.lastIndexOf("(from:");
            if (idx >= 0) desc = desc.substring(0, idx).trim();
            return String.join(" | ", "E", done, desc, isoStart, isoEnd);
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

