package UI.Controller;

import Session.EntryManagement.SecureEntry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ViewLogin {
    private long sleepTime = 10000;

    @FXML
    private PasswordField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button showButton;

    public ViewLogin()
    {
        Platform.runLater(new Runnable() {
            public void run() {
                username.setVisible(false);
                password.setVisible(false);
            }
        });
    }

    public void setupInfo(SecureEntry toShow)
    {
        String[] entries = toShow.getItems();

        this.usernameField.setText(entries[0]);
        this.passwordField.setText(entries[1]);

        this.username.setText(entries[0]);
        this.password.setText(entries[1]);
    }

    @FXML
    private void handleShowButton()
    {
        this.usernameField.setVisible(!this.usernameField.isVisible());
        this.passwordField.setVisible(!this.passwordField.isVisible());

        this.username.setVisible(!this.username.isVisible());
        this.password.setVisible(!this.password.isVisible());

        if (this.username.isVisible())
            this.showButton.setText("Hide");
        else
            this.showButton.setText("Show");
    }
}
