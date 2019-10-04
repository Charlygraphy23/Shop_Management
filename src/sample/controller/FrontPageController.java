package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.animation.FontSlider;
import sample.animation.ReverseFontSlider;
import sample.animation.Slider;
import sample.database.DBHandller;
import sample.database.DBuserController;
import sample.model.Admin;
import sample.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

public class FrontPageController {

    @FXML
    private AnchorPane frontPagepane;

    @FXML
    private AnchorPane sildePage;

    @FXML
    private AnchorPane userLoginPage;

    @FXML
    private AnchorPane adminLoginPane;

    @FXML
    private JFXButton adminButton;

    @FXML
    private JFXButton userbutton;

    @FXML
    private Label userlabel;

    @FXML
    private Label adminLabel;

    @FXML
    private JFXTextField userUsername;

    @FXML
    private JFXPasswordField userPassword;

    @FXML
    private JFXButton userLoginButton;

    @FXML
    private JFXTextField adminUsername;

    @FXML
    private JFXPasswordField adminPasssword;

    @FXML
    private JFXButton adminLoginButton;

    @FXML
    private Label userSignupButton;



    @FXML
    private Label adminSignupButton;
    public static int adminId;
    public static int userId;
    public static String alermDate;


    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    void initialize() {


        adminButton.setOnAction(a->{ getWin(); });
        userbutton.setOnAction(a->{ getAdmin(); });

        userLoginButton.setOnAction(a->{
            try {
                getUser();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        adminSignupButton.setOnMouseClicked(m->{

            DBHandller handller=new DBHandller();
            try {
                if(handller.countAdmin()>0){
                    FXMLLoader loader=new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/view/adminconf.fxml"));
                    loader.load();

                    Parent root=loader.getRoot();
                    Stage stage=new Stage();
                    stage.setScene(new Scene(root));
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();
                    adminSignupButton.getScene().getWindow().hide();
                }
                else {
                    AnchorPane root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/sample/view/adminregister.fxml"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Slider slider = new Slider(root);
                    slider.getTransition();
                    frontPagepane.getChildren().addAll(root);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        adminLoginButton.setOnAction(a->{
            try {
                adminLogin();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        userSignupButton.setOnMouseClicked(m->{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/usersignup.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root=loader.getRoot();
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            userSignupButton.getScene().getWindow().hide();
        });



    }

    private void getUser() throws SQLException, ClassNotFoundException {

        String firstName = null,lastName = null,userName = null,uDob = null,uMobNo = null,address = null,gender = null,shopId = null,openDate=null;
        int age = 0;
        Timestamp timestamp = null;

        DBuserController controller=new DBuserController();
        if(!userUsername.getText().equals("") && !userPassword.getText().equals("")){
            User user=new User();
            user.setUserName(userUsername.getText());
            user.setPassword(userPassword.getText());
            ResultSet set=controller.getUserLogin(user);
            int count=0;
            while (set.next()){
                userId=set.getInt("userid");
                firstName=set.getString("firstname");
                lastName=set.getString("lastname");
                userName=set.getString("username");
                uDob=set.getString("dob");
                uMobNo=set.getString("mobno");
                address=set.getString("address");
                gender=set.getString("gender");
                shopId=set.getString("shopid");
                age=set.getInt("age");
                timestamp=set.getTimestamp("lastupdateddate");
                alermDate=set.getString("alermdate");
                openDate=set.getString("openningdate");

                count++;
                System.out.println(count);
            }
            if(count>0){
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/userdetails.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root=loader.getRoot();
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
                userLoginButton.getScene().getWindow().hide();

            }
            else {
                System.out.println("Error");
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username and Password Invalid");
            alert.setWidth(70);
            alert.setHeight(38);
            alert.show();
            System.out.println("Error in If Block");
        }

    }


    private void adminLogin() throws SQLException, ClassNotFoundException, IOException {



        if(!adminUsername.getText().equals("") && !adminPasssword.getText().equals("")) {
            Admin admin = new Admin();
            admin.setAdminUsername(adminUsername.getText());
            admin.setAdminPassword(adminPasssword.getText());

            DBHandller handller = new DBHandller();
            ResultSet resultSet = handller.adminDetailsCheck(admin);

            int c = 0;
            while (resultSet.next()) {
                adminId=resultSet.getInt("adminid");
                c++;
            }

            if (c > 0) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/admindetails.fxml"));
                loader.load();

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();

                adminLoginButton.getScene().getWindow().hide();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username and Password Invalid");
                alert.setWidth(70);
                alert.setHeight(38);
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username and Password Invalid");
            alert.setWidth(70);
            alert.setHeight(38);
            alert.show();
        }
    }


    private void getAdmin() {

        adminLoginPane.setVisible(false);
        ReverseFontSlider fontSlider=new ReverseFontSlider(sildePage);
        fontSlider.getTransition();
        userLoginPage.setVisible(true);
        adminButton.setVisible(true);
        userbutton.setVisible(false);
        adminLabel.setVisible(false);
        userlabel.setVisible(true);
    }

    private void getWin() {

        userLoginPage.setVisible(false);
        FontSlider fontSlider =new FontSlider(sildePage);
        fontSlider.getTransition();
        adminLoginPane.setVisible(true);
        adminButton.setVisible(false);
        userbutton.setVisible(true);
        adminLabel.setVisible(true);
        userlabel.setVisible(false);


    }


}