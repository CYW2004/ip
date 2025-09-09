package xiaobai;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        assert args != null : "Arguments array must not be null";
        Application.launch(Main.class, args);
    }
}
