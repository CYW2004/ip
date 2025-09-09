package xiaobai;

/**
 * A UI used by the GUI layer that accumulates output as text
 */
public class GuiUi extends Ui {
    private final StringBuilder sb = new StringBuilder();

    public void printLine() { }

    public void printBox(String msg) {
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
