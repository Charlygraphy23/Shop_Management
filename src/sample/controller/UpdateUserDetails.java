package sample.controller;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.database.DBuserController;
import sample.model.User;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

public class UpdateUserDetails extends Window {


    @FXML
    private JFXTextField userUpdateFirstName;

    @FXML
    private JFXTextField userUpdateLastName;

    @FXML
    private JFXTextField userUpdateUsername;


    @FXML
    private JFXComboBox<String> userUpdateSelectGenderCombo;

    @FXML
    private JFXDatePicker userUpdateSelectDOB;

    @FXML
    private JFXTextField userUpdateAgeLabel;

    @FXML
    private JFXTextField userUpdateMobileNo;

    @FXML
    private JFXTextArea userUpdatePAddress;

    @FXML
    private ImageView userUpdatePhotoImageView;

    @FXML
    private JFXButton userUpdateButton;


    @FXML
    private Label userPhotoUploadButton;

    @FXML
    private ImageView userBackButton;

    private ObservableList<String> list_gender= FXCollections.observableArrayList("Male","Female","Others");
    private  InputStream inputStream = null;
    private FileInputStream fileInputStream;
    private File file;


    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void initialize(String fistName, String lastName, String userName, String gender, String mobNuber, String uDob, String uAddress, int age) {



        
        userUpdateSelectGenderCombo.setItems(list_gender);

        DBuserController controller=new DBuserController();
                
        userUpdateFirstName.setText(fistName);
        userUpdateLastName.setText(lastName);
        userUpdateUsername.setText(userName);
        userUpdateSelectGenderCombo.getSelectionModel().select(gender);
        userUpdateMobileNo.setText(mobNuber);
        userUpdateSelectDOB.setValue(LocalDate.parse(uDob));
        userUpdatePAddress.setText(uAddress);
        userUpdateAgeLabel.setText(String.valueOf(age));

        try {
            ResultSet resultSet=controller.grabUserImage();
            
            while (resultSet.next()){
               inputStream=resultSet.getBinaryStream("photo");

            }
            
            userUpdatePhotoImageView.setImage(new Image(inputStream));
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        userUpdateButton.setOnAction(a->{
            try {
                update();
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });

        userPhotoUploadButton.setOnMouseClicked(m->
        {
            FileChooser chooser=new FileChooser();
            file=chooser.showOpenDialog(this);
            if(file!=null){
                Image image=new Image(file.toURI().toString());
                userUpdatePhotoImageView.setImage(image);
            }
        });


        userBackButton.setOnMouseClicked(m->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/userdetails.fxml"));
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
            userUpdateButton.getScene().getWindow().hide();
        });
    }

    private void update() throws FileNotFoundException, SQLException {

        Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());
        int i=0;
        for(;i<userUpdateMobileNo.getText().length();i++);    // to check the 10digit of mobNo


        if(!userUpdateFirstName.getText().equals("") && !userUpdateLastName.getText().equals("") && !userUpdateUsername.getText().equals("") &&
                 userUpdateSelectGenderCombo.getSelectionModel().getSelectedItem()!=null && userUpdateSelectDOB.getValue()!=null && !userUpdateAgeLabel.getText().equals("")
                && !userUpdateMobileNo.getText().equals("") && !userUpdatePAddress.getText().equals("") && i==10){


            User user=new User();
            DBuserController controller=new DBuserController();

            user.setFirstName(userUpdateFirstName.getText().toUpperCase());
            user.setLastName(userUpdateLastName.getText().toUpperCase());
            user.setUserName(userUpdateUsername.getText());
            user.setGender(userUpdateSelectGenderCombo.getSelectionModel().getSelectedItem());
            user.setDob(userUpdateSelectDOB.getValue().toString());
            user.setAge(Integer.parseInt(userUpdateAgeLabel.getText()));
            user.setAddress(userUpdatePAddress.getText());
            user.setLastUpdatedDetails(timestamp);
            user.setMobNo((userUpdateMobileNo.getText()));
            if(file==null){
                ResultSet resultSet= null;
                try {
                    resultSet = controller.grabUserImage();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                while (resultSet.next()){
                    inputStream=resultSet.getBinaryStream("photo");
                }
                user.setPhoto(inputStream);
                System.out.println("File Inserted");
            }
           else {
                fileInputStream=new FileInputStream(file.getAbsoluteFile());
                user.setPhoto(fileInputStream);
                System.out.println("File InputStream");
           }

            try {       controller.updateUser(user);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Updated");
                        alert.setWidth(80);
                        alert.setHeight(38);
                        alert.show();
                        alert.setOnHidden(new EventHandler<DialogEvent>() {
                            @Override
                            public void handle(DialogEvent event) {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/sample/view/userdetails.fxml"));
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
                                userUpdateButton.getScene().getWindow().hide();

                            }
                        });

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid Field");
            alert.setWidth(80);
            alert.setHeight(38);
            alert.show();
        }


    }
}
