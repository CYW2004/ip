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
        assert scrollPane != null : "ScrollPane must not be null";
        assert dialogContainer != null : "DialogContainer must not be null";
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the XiaoBai instance. */
    public void setXiaoBai(XiaoBai xb) {
        assert xb != null : "XiaoBai instance must not be null";
        this.xiaoBai = xb;
    }

    /** Handles send button and Enter key. */
    @FXML
    private void handleUserInput() {
        assert userInput != null : "UserInput field must not be null";
        assert dialogContainer != null : "DialogContainer must not be null";
        assert xiaoBai != null : "XiaoBai instance must be set before handling input";
        String input = userInput.getText();
        if (input == null || input.isBlank()) return;

        String response = xiaoBai.getResponse(input);
        assert response != null : "Response must not be null";
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
