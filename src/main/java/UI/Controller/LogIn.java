package UI.Controller;

import Session.SessionInfo;
import UI.Enum.EWindow;
import UI.WindowSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class LogIn {
    @FXML
    private PasswordField passwordField;

    public void onConfirmPressed()
    {
        WindowSwitcher.setSessionInfo(new SessionInfo(passwordField.getText()));
        WindowSwitcher.switchWindow(EWindow.MainMenu);
    }
}
