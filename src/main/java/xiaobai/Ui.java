package xiaobai;

public class Ui {
    public void printLine() {
        System.out.println("____________________________________________________________");
    }
    public void printBoxed(String... lines) {
        printLine();
        for (String line : lines) {
            System.out.println("" + line);
        }
        printLine();
    }
    public void printErrorBox(String msg) {
        printLine();
        System.out.println(" " + msg);
        printLine();
    }
}

