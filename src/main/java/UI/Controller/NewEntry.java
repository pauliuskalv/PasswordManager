package UI.Controller;

import Session.EntryManagement.Enum.EEntryType;
import Session.EntryManagement.SecureEntry;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewEntry {
    private Stage stage;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private VBox entryPane;
    @FXML
    private TextField name;

    private boolean firstStart;

    private EEntryType currentEntry;

    /**
     * Password entry elements
     * */
    private Label usernameLabel;
    private Label passwordLabel;
    private TextField username;
    private PasswordField password;

    /**
     * Note entry elements
     * */
    private TextArea note;

    /**
     * TODO
     * Custom entry elements
     * */

    private SecureEntry createdEntry;

    public NewEntry()
    {
        Platform.runLater(new Runnable() {
            public void run() {
                ObservableList<String> choices = FXCollections.observableArrayList();

                choices.setAll(
                        "New Password",
                        "New Note"
                        );

                choiceBox.setItems(choices);
                choiceBox.setValue("New Password");

                previewItems(EEntryType.Password);

                entryPane.setAlignment(Pos.CENTER);
            }
        });
    }

    private void previewItems(EEntryType type)
    {
        this.currentEntry = type;

        Parent root = null;

        switch (type)
        {
            case Password:
            {
                GridPane toCreate = new GridPane();
                toCreate.setAlignment(Pos.CENTER);

                this.usernameLabel = new Label("Username: ");
                this.passwordLabel = new Label("Password: ");
                this.username = new TextField();
                this.password = new PasswordField();

                GridPane.setConstraints(this.usernameLabel, 0,2);
                GridPane.setConstraints(this.passwordLabel,0,4);
                GridPane.setConstraints(this.username,1,2);
                GridPane.setConstraints(this.password,1,4);

                toCreate.getChildren().addAll(
                        this.usernameLabel,
                        this.passwordLabel,
                        this.username,
                        this.password
                );

                root = toCreate;

                break;
            }
            case Note:
            {
                VBox toCreate = new VBox();

                this.note = new TextArea();
                this.note.setWrapText(true);

                toCreate.getChildren().add(this.note);

                root = toCreate;

                break;
            }
            case Custom:
            {
                /**
                 * TODO
                 * */
                break;
            }
        }

        this.entryPane.getChildren().add(root);
    }

    public void setScene(Stage toSet)
    {
        this.stage = toSet;
    }

    private void clearElements()
    {
        if (!firstStart)
        {
            firstStart = true;
            return;
        }

        this.entryPane.getChildren().remove(0,1);
        switch (this.currentEntry)
        {
            case Password:
            {
                this.usernameLabel = null;
                this.passwordLabel = null;
                this.username = null;
                this.password = null;
            }
            case Note:
            {
                this.note = null;
            }
            case Custom:
            {
                /**
                 * TODO
                 * */
                break;
            }
        }
    }

    @FXML
    private void choiceChanged()
    {
        clearElements();
        switch (this.choiceBox.getSelectionModel().getSelectedIndex())
        {
            case 0:
            {
                previewItems(EEntryType.Password);
                break;
            }
            case 1:
            {
                previewItems(EEntryType.Note);
                break;
            }
            case 2:
            {
                previewItems(EEntryType.Custom);
                break;
            }
        }
    }

    @FXML
    private void handleExitButton()
    {
        switch (this.currentEntry)
        {
            case Password:
            {
                this.createdEntry = new SecureEntry(
                        this.name.getText(),
                        "AES",
                        new String[] {
                                this.username.getText(),
                                this.password.getText()
                        },
                        this.currentEntry
                        );
                break;
            }
            case Note:
            {
                this.createdEntry = new SecureEntry(
                        this.name.getText(),
                        "AES",
                        new String[] {
                                this.note.getText()
                        },
                        EEntryType.Note
                );
                break;
            }
            case Custom:
            {
                /**
                 * TODO
                 * */
                break;
            }
        }
        this.stage.close();
    }

    public SecureEntry getResult()
    {
        return this.createdEntry;
    }
}
