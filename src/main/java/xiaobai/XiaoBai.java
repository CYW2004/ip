package xiaobai;

import java.util.Scanner;

public class XiaoBai {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.printBoxed( "\n__  __ ___    _    ___   ____    _    ___\n"
                + "\\ \\/ /|_ _|  / \\  / _ \\ | __ )  / \\  |_ _|\n"
                + " \\  /  | |  / _ \\| | | ||  _ \\ / _ \\  | |\n"
                + " /  \\  | | / ___ \\ |_| || |_) / ___ \\ | |\n"
                + "/_/\\_\\|___/_/   \\_\\___/ |____/_/   \\_\\___|\n"
        );

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

    public static void main(String[] args) {
        new XiaoBai("data/xiaobai.txt").run();
    }
}
