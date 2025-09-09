package xiaobai;

/**
 * A UI used by the GUI layer that accumulates output as text
 */
public class GuiUi extends Ui {
    private final StringBuilder sb = new StringBuilder();

    public void printLine() { /* no-op */ }

    public void printBox(String msg) {        // override to be GUI-friendly
        sb.append(msg).append(System.lineSeparator());
    }

    public void printErrorBox(String msg) {
        sb.append(msg).append(System.lineSeparator());
    }

    public String getText() {
        String out = sb.toString().trim();
        sb.setLength(0);
        return out;
    }
}
