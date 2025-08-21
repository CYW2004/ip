import java.util.Scanner;

public class XiaoBai {

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }

    private static void printBoxed(String... lines) {
        printLine();
        for (String line : lines) {
            System.out.println(" " + line);
        }
        printLine();
    }

    private static void printErrorBox(String msg) {
        printLine();
        System.out.println(" " + msg);
        printLine();
    }

    private static int parseIndexOrThrow(String raw) throws XiaoBaiException {
        try {
            int idx = Integer.parseInt(raw.trim()) - 1;
            return idx;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Use: mark|unmark <task number>");
        }
    }

    private static void ensureIndexInRange(int index, int taskCount) throws XiaoBaiException {
        if (index < 0 || index >= taskCount) throw new InvalidIndexException();
    }

    public static void main(String[] args) throws XiaoBaiException {
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        String logo_text = "__  __ ___    _    ___   ____    _    ___\n"
                        + "\\ \\/ /|_ _|  / \\  / _ \\ | __ )  / \\  |_ _|\n"
                        + " \\  /  | |  / _ \\| | | ||  _ \\ / _ \\  | |\n"
                        + " /  \\  | | / ___ \\ |_| || |_) / ___ \\ | |\n"
                        + "/_/\\_\\|___/_/   \\_\\___/ |____/_/   \\_\\___|\n";
        String expression = "(*^_^*)";

        System.out.println("____________________________________________________________");
        System.out.println();
        System.out.println(logo_text);
        printBoxed(expression, "Hello! I'm XiaoBai", "What can I do for you?");

        while (true) {
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    printBoxed("(^-^) Bye~ Hope to see you again soon!");
                    break;

                } else if (input.equalsIgnoreCase("list")) {
                    printLine();
                    if (taskCount == 0) {
                        System.out.println(" Your list is empty.");
                    } else {
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < taskCount; i++) {
                            System.out.println(" " + (i + 1) + "." + tasks[i]);
                        }
                    }
                    printLine();

                } else if (input.startsWith("mark ")) {
                    int index = parseIndexOrThrow(input.substring(5));
                    ensureIndexInRange(index, taskCount);
                    tasks[index].markAsDone();
                    printBoxed("Nice! I've marked this task as done:", "  " + tasks[index]);

                } else if (input.startsWith("unmark ")) {
                    int index = parseIndexOrThrow(input.substring(7));
                    ensureIndexInRange(index, taskCount);
                    tasks[index].markAsNotDone();
                    printBoxed("OK, I've marked this task as not done yet:", "  " + tasks[index]);

                } else if (input.startsWith("todo")) {
                    String description = input.length() >= 5 ? input.substring(5).trim() : "";
                    if (description.isEmpty()) throw new EmptyDescriptionException("todo");
                    Task t = new Todo(description);
                    tasks[taskCount++] = t;
                    printBoxed(
                            "Got it. I've added this task:",
                            "  " + t,
                            "Now you have " + taskCount + " tasks in the list."
                    );

                } else if (input.startsWith("deadline")) {
                    String payload = input.length() >= 9 ? input.substring(9) : "";
                    String[] parts = payload.split("/by", 2);
                    if (parts.length < 2) {
                        throw new InvalidFormatException("Use: deadline <description> /by <when>");
                    }
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    if (desc.isEmpty()) throw new EmptyDescriptionException("deadline");
                    if (by.isEmpty()) throw new InvalidFormatException("The /by time cannot be empty.");
                    Task d = new Deadline(desc, by);
                    tasks[taskCount++] = d;
                    printBoxed(
                            "Got it. I've added this task:",
                            "  " + d,
                            "Now you have " + taskCount + " tasks in the list."
                    );

                } else if (input.startsWith("event")) {
                    String payload = input.length() >= 6 ? input.substring(6) : "";
                    String[] fromSplit = payload.split("/from", 2);
                    if (fromSplit.length < 2) {
                        throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
                    }
                    String desc = fromSplit[0].trim();
                    String[] toSplit = fromSplit[1].split("/to", 2);
                    if (toSplit.length < 2) {
                        throw new InvalidFormatException("Use: event <description> /from <start> /to <end>");
                    }
                    String from = toSplit[0].trim();
                    String to = toSplit[1].trim();
                    if (desc.isEmpty()) throw new EmptyDescriptionException("event");
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new InvalidFormatException("The /from and /to times cannot be empty.");
                    }
                    Task e = new Event(desc, from, to);
                    tasks[taskCount++] = e;
                    printBoxed(
                            "Got it. I've added this task:",
                            "  " + e,
                            "Now you have " + taskCount + " tasks in the list."
                    );

                } else {
                    throw new UnknownCommandException(input);
                }

            } catch (XiaoBaiException xe) {
                printErrorBox(xe.getMessage());
            }
        }

        scanner.close();
    }
}
