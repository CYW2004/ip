import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XiaoBai {

    public static void main(String[] args) throws XiaoBaiException {
        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        Ui ui = new Ui();

        String logo_text = "__  __ ___    _    ___   ____    _    ___\n"
                + "\\ \\/ /|_ _|  / \\  / _ \\ | __ )  / \\  |_ _|\n"
                + " \\  /  | |  / _ \\| | | ||  _ \\ / _ \\  | |\n"
                + " /  \\  | | / ___ \\ |_| || |_) / ___ \\ | |\n"
                + "/_/\\_\\|___/_/   \\_\\___/ |____/_/   \\_\\___|\n";
        String expression = "(*^_^*)";

        ui.printLine();
        System.out.println();
        System.out.println(logo_text);
        ui.printBoxed(expression, "Hello! I'm XiaoBai", "What can I do for you?");

        while (true) {
            if (!scanner.hasNextLine()) break;
            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("bye")) {
                    ui.printBoxed("(¦3[▓▓] Bye! Hope to see you again soon!");
                    break;
                } else if (input.equalsIgnoreCase("list")) {
                    CommandHandler.listTasks(tasks, ui);

                } else if (input.startsWith("mark ")) {
                    CommandHandler.markTask(tasks, input.substring(5), true, ui);

                } else if (input.startsWith("unmark ")) {
                    CommandHandler.markTask(tasks, input.substring(7), false, ui);

                } else if (input.startsWith("delete ")) {
                    CommandHandler.deleteTask(tasks, input.substring(7), ui);

                } else if (input.startsWith("todo")) {
                    String rest = input.length() > 4 ? input.substring(4) : "";
                    CommandHandler.addTodo(tasks, rest, ui);

                } else if (input.startsWith("deadline")) {
                    String rest = input.length() > 8 ? input.substring(8) : "";
                    CommandHandler.addDeadline(tasks, rest, ui);

                } else if (input.startsWith("event")) {
                    String rest = input.length() > 5 ? input.substring(5) : "";
                    CommandHandler.addEvent(tasks, rest, ui);

                } else {
                    throw new UnknownCommandException(input);
                }

            } catch (XiaoBaiException xe) {
                ui.printErrorBox(xe.getMessage());
            }
        }

        scanner.close();
    }
}
