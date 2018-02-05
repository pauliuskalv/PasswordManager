package UI;

import Session.SessionInfo;
import UI.Controller.MainMenu;
import UI.Enum.EWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class WindowSwitcher {
    private static Stage mainWindow;
    private static Connection databaseConn;
    private static SessionInfo sessionInfo;

    private static Object classLoader = new Object();

    public static void switchWindow(EWindow window)
    {
        try {
            FXMLLoader layoutLoader = null;
            Parent root = null;
            switch (window)
            {
                case LogIn:
                {
                    layoutLoader = new FXMLLoader(classLoader.getClass().getResource("/LogIn.fxml"));
                    break;
                }
                case MainMenu:
                {
                    layoutLoader = new FXMLLoader(classLoader.getClass().getResource("/MainMenu.fxml"));
                    break;
                }
            }

            root = layoutLoader.load();

            /**
             * Some windows need additional information set
             * */
            switch (window)
            {
                case MainMenu:
                {
                    layoutLoader.<MainMenu>getController().setMasterPassword(sessionInfo.getMasterPassword());
                    break;
                }
            }

            mainWindow.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading layout!");
            System.exit(0);
        }
    }

    public static void setStage(Stage window)
    {
        mainWindow = window;
    }

    public static void setConnection(Connection conn)
    {
        databaseConn = conn;
    }

    public static void setSessionInfo(SessionInfo info)
    {
        sessionInfo = info;
    }

    private static void createModalWindow(Scene layout)
    {

    }
}
