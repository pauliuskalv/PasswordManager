package UI;

import IO.Database;
import UI.Enum.EWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;

public class LogIn extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        /**
         * Set the primary window class
         * */
        WindowSwitcher.setStage(primaryStage);

        /**
         * Create the database object
         * */
        Database.getInstance().init();

        /**
         * Go to the login window
         * */
        WindowSwitcher.switchWindow(EWindow.LogIn);

        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
