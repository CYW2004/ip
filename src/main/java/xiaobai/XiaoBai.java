package xiaobai;

import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Entry point of the XiaoBai task management program.
 * Handles initialization, user interaction loop, and command execution.
 */
public class XiaoBai {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates a XiaoBai instance with the given file path for task storage.
     * Loads tasks from disk, or initializes an empty task list if loading fails.
     *
     * @param filePath Path to the storage file.
     */
    public XiaoBai(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load(ui));
        } catch (Exception e) {
            ui.printErrorBox("(>_<) Failed to load tasks: " + e.getMessage());
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the XiaoBai program.
     * Displays a greeting, reads and executes user commands.
     * Terminates if exit command is given.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String logo_text = "\n__  __ ___    _    ___   ____    _    ___\n"
                + "\\ \\/ /|_ _|  / \\  / _ \\ | __ )  / \\  |_ _|\n"
                + " \\  /  | |  / _ \\| | | ||  _ \\ / _ \\  | |\n"
                + " /  \\  | | / ___ \\ |_| || |_) / ___ \\ | |\n"
                + "/_/\\_\\|___/_/   \\_\\___/ |____/_/   \\_\\___|\n";

        ui.printBoxed(logo_text);

        ui.printBoxed(" (*^_^*)\n" +
                " Hello! I'm XiaoBai\n" +
                " What can I do for you?"
        );

        boolean isExit = false;
        while (!isExit && scanner.hasNextLine()) {
            String fullCommand = scanner.nextLine();
            try {
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (XiaoBaiException xe) {
                ui.printErrorBox(xe.getMessage());
            }
        }
    }

    public String getResponse(String input) {
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;

        ByteArrayOutputStream outBuf = new ByteArrayOutputStream();
        ByteArrayOutputStream errBuf = new ByteArrayOutputStream();
        PrintStream capOut = new PrintStream(outBuf, true, StandardCharsets.UTF_8);
        PrintStream capErr = new PrintStream(errBuf, true, StandardCharsets.UTF_8);

        System.setOut(capOut);
        System.setErr(capErr);

        String uiText = "";
        try {
            Command c = Parser.parse(input);

            GuiUi guiUi = new GuiUi();
            c.execute(tasks, guiUi, storage);
            uiText = guiUi.getText();

        } catch (XiaoBaiException xe) {
            uiText = xe.getMessage();
        } catch (Exception e) {
            uiText = "☹ OOPS!!! " + (e.getMessage() == null ? e.toString() : e.getMessage());
        } finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
        }

        String printed = outBuf.toString(StandardCharsets.UTF_8);
        String printedErr = errBuf.toString(StandardCharsets.UTF_8);

        StringBuilder reply = new StringBuilder();
        if (uiText != null && !uiText.isBlank()) reply.append(uiText.trim());
        if (!printed.isBlank()) {
            if (reply.length() > 0) reply.append(System.lineSeparator());
            reply.append(printed.trim());
        }
        if (!printedErr.isBlank()) {
            if (reply.length() > 0) reply.append(System.lineSeparator());
            reply.append(printedErr.trim());
        }

        String result = reply.toString();
        return result.isBlank() ? "(no output)" : result;
    }

    public static void main(String[] args) {
        new XiaoBai("data/xiaobai.txt").run();
    }
}
