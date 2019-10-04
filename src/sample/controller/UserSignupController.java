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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.database.DBHandller;
import sample.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class UserSignupController extends Window {

    @FXML
    private AnchorPane userPane;

    @FXML
    private JFXTextField userFirstName;

    @FXML
    private JFXTextField userLastName;

    @FXML
    private JFXTextField userUsername;

    @FXML
    private JFXPasswordField userPassword;

    @FXML
    private JFXComboBox<String> userSelectshopCombo;

    @FXML
    private JFXComboBox<String> userSelectGenderCombo;

    @FXML
    private JFXDatePicker userSelectDOB;

    @FXML
    private JFXTextField userAgeLabel;

    @FXML
    private JFXTextField userMobileNo;

    @FXML
    private JFXTextArea userPAddress;

    @FXML
    private ImageView userPhotoImageView;

    @FXML
    private Label userPhotoUploadButton;

    @FXML
    private JFXButton userSignupButton;

    @FXML
    private JFXDatePicker userShopOpenDate;

    private ObservableList<String> list_gender= FXCollections.observableArrayList("Male","Female","Others");
    private ObservableList<String> list_Shop= FXCollections.observableArrayList("SP1","SP2","SP3");
    private FileInputStream fileInputStream;
    private File file;
    int day;
    private LocalDate now;


    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void initialize() {

        userSelectGenderCombo.setItems(list_gender);
        userSelectshopCombo.setItems(list_Shop);


        userSelectDOB.setOnAction(a->{
            if(userSelectDOB.getValue()!=null){
                int uAge=Calendar.getInstance().getWeekYear() - userSelectDOB.getValue().getYear();
                userAgeLabel.setText(String.valueOf(uAge));
            }
            else {
                userAgeLabel.setText("");
            }
        });

        userSignupButton.setOnAction(a->{
            try {
                signUp();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        userPhotoUploadButton.setOnMouseClicked(m->{
            uploadImage();
        });

    }

    private void uploadImage() {

        FileChooser chooser=new FileChooser();
        file=chooser.showOpenDialog(this);
        if(file!=null){
            userPhotoImageView.setImage(new Image(file.toURI().toString()));
        }

    }

    private void signUp() throws SQLException, ClassNotFoundException, FileNotFoundException {
        Timestamp timestamp=new Timestamp(Calendar.getInstance().getWeekYear());
        int i=0;
        for(;i<userMobileNo.getText().length();i++);    // to check the 10digit of mobNo

        now = LocalDate.now();
        LocalDate openDate = userShopOpenDate.getValue();

        Period p = Period.between(now, openDate);
        int days = Math.abs(p.getDays());
        int months = Math.abs(p.getMonths());
        System.out.println("Days " + days);
        if (months > 0) {
            day = (months * 30) + days;
            System.out.println("Days " + days);
        }
        else {
            day=openDate.getDayOfMonth();
        }




        if(day>=now.getDayOfMonth() && !userFirstName.getText().equals("") && !userLastName.getText().equals("") && !userUsername.getText().equals("") && !userPassword.getText().equals("")
        && userSelectshopCombo.getSelectionModel().getSelectedItem()!=null && userSelectGenderCombo.getSelectionModel().getSelectedItem()!=null && userSelectDOB.getValue()!=null && !userAgeLabel.getText().equals("")
        && !userMobileNo.getText().equals("") && !userPAddress.getText().equals("") && userPhotoImageView.getImage()!=null && userShopOpenDate.getValue()!=null && i==10){


            fileInputStream=new FileInputStream(file.getAbsoluteFile());

            DBHandller handller=new DBHandller();
            User user=new User();
            user.setFirstName(userFirstName.getText().toUpperCase());
            user.setLastName(userLastName.getText().toUpperCase());
            user.setUserName(userUsername.getText());
            user.setPassword(userPassword.getText());
            user.setGender(userSelectGenderCombo.getSelectionModel().getSelectedItem());
            user.setShopId(userSelectshopCombo.getSelectionModel().getSelectedItem());
            user.setDob(userSelectDOB.getValue().toString());
            user.setAge(Integer.parseInt(userAgeLabel.getText()));
            user.setAddress(userPAddress.getText());
            user.setPhoto(fileInputStream);
            user.setOpenDate(userShopOpenDate.getValue().toString());
            user.setLastUpdatedDetails(timestamp);
            user.setAlermDate(Date.valueOf(userShopOpenDate.getValue()));
            user.setMobNo((userMobileNo.getText()));


            if(handller.getShopDetails(user)==0) {
                if (handller.signUpUser(user) == 0) {
                    handller.signUpUser(user);
                    System.out.println("Data inserted");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Created");
                    alert.setWidth(80);
                    alert.setHeight(38);
                    alert.show();
                    alert.setOnHidden(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent event) {
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
                            userSignupButton.getScene().getWindow().hide();

                        }
                    });
                }
            }
            else if(i!=10){

                    System.out.println("The Number is Incorrect");
                    Alert alert=new Alert(Alert.AlertType.ERROR,"Please enter a Valid Number");
                    alert.setWidth(80);
                    alert.setHeight(38);
                    alert.show();
            }
            else if(handller.getShopDetails(user)==1){
                Alert alert=new Alert(Alert.AlertType.ERROR," ShopName has already been taken");
                alert.setWidth(80);
                alert.setHeight(38);
                alert.show();
            }

            else {
                System.out.println("Username or Shop Name has already been taken");
                Alert alert=new Alert(Alert.AlertType.ERROR,"Username has already been taken");
                alert.setWidth(80);
                alert.setHeight(38);
                alert.show();
            }
        }

        else {
            System.out.println("Error In If Block");
            Alert alert=new Alert(Alert.AlertType.ERROR,"Invalid users Details");
            alert.setWidth(80);
            alert.setHeight(38);
            alert.show();
        }

    }



}
