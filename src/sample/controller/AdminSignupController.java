package sample.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.database.DBHandller;
import sample.model.Admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

public class AdminSignupController extends Window {



    @FXML
    private JFXTextField adminFirstNmae;

    @FXML
    private JFXTextField adminLastName;

    @FXML
    private JFXTextField adminUserName;

    @FXML
    private JFXPasswordField adminPassword;

    @FXML
    private JFXComboBox<String> adminGender;

    @FXML
    private JFXDatePicker adminAge;

    @FXML
    private JFXButton adminSignUpButton;

    @FXML
    private JFXTextField adminShowAge;

    @FXML
    private ImageView adminImageView;

    @FXML
    private ImageView adminUploadButton;

    @FXML
    private Label adminConfirmationLabel;

    @FXML
    private ImageView adminLogoutButton;

    @FXML
    private AnchorPane ovarAllpane;

    @FXML
    private AnchorPane adminDetailsPane;

    private ObservableList<String> genderList= FXCollections.observableArrayList("Male","Female","Other");
    private int age;
    private DBHandller handller;

    private FileInputStream fileInputStream;
    private File file;


    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

        adminGender.setItems(genderList);


           adminAge.setOnAction(a->{
               if(adminAge.getValue()!=null) {
                   age = Calendar.getInstance().getWeekYear() - adminAge.getValue().getYear();
                   adminShowAge.setText(Integer.toString(age));
               }
               else {
                   adminShowAge.setText("");
               }
           });


        adminSignUpButton.setOnAction(a->{

            try {
                getAdminDetails();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        adminUploadButton.setOnMouseClicked(m->{
            getPhoto();
        });

        ovarAllpane.setOnMouseClicked(m->{
            adminConfirmationLabel.setVisible(false);
        });

        adminLogoutButton.setOnMouseClicked(m->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/fontpagesample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            adminLogoutButton.getScene().getWindow().hide();
        });


    }

    private void getPhoto() {

        FileChooser fileChooser=new FileChooser();
        file=fileChooser.showOpenDialog(this);
        if(file!=null){
            Image photo=new Image(file.toURI().toString());
            adminImageView.setImage(photo);
        }

    }


    private void getAdminDetails() throws FileNotFoundException, SQLException, ClassNotFoundException {
        handller=new DBHandller();
        Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());

        if(!adminFirstNmae.getText().equals("") && !adminLastName.getText().equals("") && !adminUserName.getText().equals("") && !adminPassword.getText().equals("") && adminGender.getSelectionModel().getSelectedItem()!=null
        && adminAge.getValue() !=null && !adminShowAge.getText().equals("") && file!=null){



            fileInputStream=new FileInputStream(file.getAbsoluteFile());

            Admin admin=new Admin();
            admin.setAdminFirstname(adminFirstNmae.getText().toUpperCase());
            admin.setAdminLastname(adminLastName.getText().toUpperCase());
            admin.setAdminUsername(adminUserName.getText());
            admin.setAdminPassword(adminPassword.getText());
            admin.setAdminGender(adminGender.getSelectionModel().getSelectedItem());
            admin.setAdminAge(Integer.parseInt(adminShowAge.getText()));
            admin.setAdminDOB(adminAge.getValue().toString());
            admin.setPhoto(fileInputStream);
            admin.setDateCreated(timestamp);

            if(handller.signUpAdmin(admin)==0){
                handller.signUpAdmin(admin);
                System.out.println("Data Inserted");
                adminConfirmationLabel.setVisible(true);
                clearAll();
            }

            else {
                Alert alert=new Alert(Alert.AlertType.ERROR,"Username has been already taken");
                alert.setWidth(70);
                alert.setHeight(38);
                alert.show();
            }
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Invalid user details");
            alert.setWidth(70);
            alert.setHeight(38);
            alert.show();
        }

    }

    private void clearAll() {

        adminFirstNmae.setText("");
        adminLastName.setText("");
        adminUserName.setText("");
        adminPassword.setText("");
        adminShowAge.setText("");
        adminGender.getSelectionModel().clearSelection();
        adminAge.setValue(null);
        adminImageView.setImage(null);


    }

}
