package xiaobai;

/**
 * Handles user interaction by printing messages, errors, and dividers to the console.
 */
public class Ui {
    /**
     * Prints a horizontal divider line.
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints text sandwiched by two divider lines.
     *
     * @param lines Lines of text.
     */
    public void printBoxed(String... lines) {
        printLine();
        for (String line : lines) {
            System.out.println("" + line);
        }
        printLine();
    }

    /**
     * Prints an error message sandwiched by divider lines.
     *
     * @param msg Error message to display.
     */
    public void printErrorBox(String msg) {
        printLine();
        System.out.println(" " + msg);
        printLine();
    }
}

