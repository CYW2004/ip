package xiaobai;

/**
 * Handles user interaction by printing messages, errors, and dividers to the console.
 * No longer useful in the latest version, but still kept here as a reference.
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
        assert lines != null : "Lines must not be null";
        printLine();
        for (String line : lines) {
            assert line != null : "Line must not be null";
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
        assert msg != null : "Error message must not be null";
        printLine();
        System.out.println(" " + msg);
        printLine();
    }
}
