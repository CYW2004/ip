package xiaobai;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private XiaoBai xiaoBai;

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/User.png"));
    private final Image botImage  = new Image(getClass().getResourceAsStream("/images/XiaoBai.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the XiaoBai instance. */
    public void setXiaoBai(XiaoBai xb) {
        this.xiaoBai = xb;
    }

    /** Handles send button and Enter key. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) return;

        String response = xiaoBai.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBotDialog(response, botImage)
        );
        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            Platform.exit();
        }
    }
}
