package sample.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DBuserController;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class UserDetailsController {


    @FXML
    private Label detailsNmae;

    @FXML
    private Label detailsUsername;

    @FXML
    private Label detailsGender;

    @FXML
    private Label detailsDob;

    @FXML
    private Label detailsAge;

    @FXML
    private ImageView adminPhoto;

    @FXML
    private Label detailsLastupdate;

    @FXML
    private FontAwesomeIconView updateDetailsWindow;

    @FXML
    private ImageView userLogOutButton;

    @FXML
    private Label detailsID;

    @FXML
    private Label detailsMobno;

    @FXML
    private Label detailsAddress;

    @FXML
    private Label detailsShopId;

    @FXML
    private Label detailsOpenningDate;

    @FXML
    private JFXButton payButton;

    @FXML
    private JFXButton viewTransaction;

    private InputStream image = null;

    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }



    private String firstName = null,lastName = null,userName = null,uDob = null,uMobNo = null,address = null,gender = null,shopId = null,openDate=null;
    private int age = 0;
    private Timestamp timestamp = null;

    @FXML
    void initialize() {

        try {
            getUserDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        LocalDate now=LocalDate.now();
        LocalDate alermDate= LocalDate.parse(FrontPageController.alermDate);

        Period p=Period.between(alermDate,now);
        int days=Math.abs(p.getDays());
        int months=Math.abs(p.getMonths());
        if(months>0) {
            days = (months * 30) + days;
            System.out.println("Now total Days " + days);
        }
        if(days>=30){
            System.out.println("Wanning");
        }
        else {
            System.out.println("Good to go");
        }



        userLogOutButton.setOnMouseClicked(m->{
            logOut();
        });

        updateDetailsWindow.setOnMouseClicked(m->{
            getUpdateWindow();
        });




        payButton.setOnAction(a->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/pay.fxml"));
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
            payButton.getScene().getWindow().hide();

        });

        viewTransaction.setOnAction(a->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/listtransaction.fxml"));
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
            viewTransaction.getScene().getWindow().hide();
        });
    }

    private void getUserDetails() throws SQLException, ClassNotFoundException {
        DBuserController controller=new DBuserController();


        ResultSet set=controller.getUserDetails();
        int count=0;

        while (set.next()){
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
            openDate=set.getString("openningdate");
            image=set.getBinaryStream("photo");
            count++;
        }
        if(count>0){

            detailsID.setText(String.valueOf(FrontPageController.userId));
            detailsNmae.setText(firstName+" "+lastName);
            detailsGender.setText(gender);
            detailsAddress.setText(address);
            detailsAge.setText(String.valueOf(age));
            detailsDob.setText(uDob);
            detailsLastupdate.setText("Last Updated Date "+String.valueOf(timestamp));
            detailsOpenningDate.setText("Openning Date "+openDate);
            detailsMobno.setText(uMobNo);
            detailsUsername.setText(userName);
            detailsShopId.setText(shopId);
            adminPhoto.setImage(new Image(image));

        }



    }

    private void getUpdateWindow() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/updateuser.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        UpdateUserDetails updateUserDetails=loader.getController();
        updateUserDetails.initialize(firstName,lastName,userName,gender,uMobNo,uDob,address,age);
        stage.show();
        updateDetailsWindow.getScene().getWindow().hide();

    }

    private void logOut() {
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
        userLogOutButton.getScene().getWindow().hide();
    }
}
