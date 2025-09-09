package xiaobai;

import java.io.IOException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            dialog.setWrapText(true);
            dialog.setMaxWidth(280);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setWrapText(true);
        dialog.setMaxWidth(300);
        dialog.setText(sanitize(text));
        displayPicture.setImage(img);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    private static String sanitize(String s) {
        if (s == null) return "";
        String noAnsi = s.replaceAll("\\u001B\\[[;\\d]*m", "");
        String ascii = noAnsi
                .replace('┌', '+').replace('┐', '+').replace('└', '+').replace('┘', '+')
                .replace('│', '|').replace('─', '-').replace('┬', '+').replace('┴', '+')
                .replace('┤', '+').replace('├', '+').replace('┼', '+');
        ascii = ascii.replaceAll("[^\\p{Print}\\n\\r\\t]", "?");
        return ascii.replace("\r\n", "\n");
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}