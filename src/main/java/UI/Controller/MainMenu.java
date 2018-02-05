package UI.Controller;

import Encryption.AESCipher;
import IO.Database;
import Session.EntryManagement.SecureEntry;
import Session.SessionInfo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class MainMenu {
    @FXML
    private ListView<String> entries;
    @FXML
    private Button optionsButton;
    @FXML
    private Button newEntryButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox rightLayout;

    /* --------------------------- */
    private SessionInfo currentSession;
    private SecretKeySpec secretKey;
    private boolean deleted = false;

    public MainMenu() {
        Platform.runLater(new Runnable() {
            public void run() {
                updateList();
                deleteButton.setVisible(false);

                /**
                 * Set up the list actions
                 * */
                entries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (deleted)
                        {
                            deleted = false;
                            return;
                        }

                        if (observableValue.getValue().equals(null))
                            return;

                        Database db = Database.getInstance();
                        byte[] encryptedObject = db.getObject(observableValue.getValue());

                        if (encryptedObject == null)
                            return;

                        encryptedObject = AESCipher.decrypt(secretKey, encryptedObject);

                        ByteArrayInputStream inputStream = new ByteArrayInputStream(encryptedObject);
                        deleteButton.setVisible(true);
                        try {
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                            SecureEntry entry = (SecureEntry)objectInputStream.readObject();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void setMasterPassword(SecretKeySpec masterPassword)
    {
        this.secretKey = masterPassword;
    }

    private void updateList()
    {
        Database db = Database.getInstance();
        ObservableList<String> items = FXCollections.observableArrayList();
        String[] entryNames = db.getList();

        if (entryNames != null)
        {
            items.addAll(db.getList());
            entries.setItems(items);
        }
    }

    @FXML
    private void handleNewEntryButton()
    {
        /**
         * TODO
         * Call a modal window here
         * */
        Stage modalWindow = new Stage();
        NewEntry entry = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateNewEntry.fxml"));
            Parent root = loader.load();
            entry = loader.getController();
            modalWindow.setScene(new Scene(root));
            entry.setScene(modalWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }

        modalWindow.initModality(Modality.APPLICATION_MODAL);
        modalWindow.showAndWait();

        Database db = Database.getInstance();
        try {
            String name = null;
            SecureEntry toGet = entry.getResult();

            if (toGet == null)
                return;
            else
                name = toGet.getName();

            System.out.println(name);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(entry.getResult());

            byte[] object = outputStream.toByteArray();
            /**
             * Encrypt the object using the master password
             * */
            object = AESCipher.encrypt(this.secretKey,object);
            db.insertObject(name,object);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        updateList();
    }

    @FXML
    private void handleDeleteButton()
    {
        Database db = Database.getInstance();
        db.deleteObject(this.entries.getSelectionModel().getSelectedItem());
        updateList();
        this.deleteButton.setVisible(false);
        this.deleted = true;
    }

    private void showObject(SecureEntry toShow)
    {
        FXMLLoader loader = null;
        switch (toShow.getType())
        {
            case Password:
            {
                loader = new FXMLLoader(getClass().getResource("/ViewLogin.fxml"));

            }
        }
    }

    @FXML
    private void handleExitButton()
    {

    }

    @FXML
    private void handleOptionsButton()
    {

    }
}
