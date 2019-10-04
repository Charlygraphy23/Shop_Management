package sample.controller;

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
import sample.database.DBHandller;
import sample.database.DBuserController;
import sample.model.Admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AdminDetailsController {

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
    private Label detailsLastupdate;

    @FXML
    private ImageView adminUpdatedetailsButton;

    @FXML
    private ImageView adminLogOutButton;

    @FXML
    private ImageView adminPhoto;
    private InputStream inputStream=null;

    @FXML
    private Label viewUsers;


    private String firstName=null,lastName=null,userName=null,gender=null,dob=null,pass=null;
    private int age=0;
    private Timestamp date=null;

    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {


        try {
            getAdminDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adminLogOutButton.setOnMouseClicked(m->{
            try {
                adminLogOut();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        adminUpdatedetailsButton.setOnMouseClicked(m->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/adminupdate.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            AdminUpdateController updateController=loader.getController();
            try {
                updateController.initialize(firstName,lastName,userName,gender,dob,age,pass);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            adminUpdatedetailsButton.getScene().getWindow().hide();
        });

        viewUsers.setOnMouseClicked(m->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/adminuserdetails.fxml"));
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
            viewUsers.getScene().getWindow().hide();
        });

    }

    private void getAdminDetails() throws SQLException {
        DBuserController handller = new DBuserController();
        ResultSet resultSet = null;
        try {
            resultSet = handller.getAdminDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int c = 0;
        while (resultSet.next()) {
            firstName = resultSet.getString("firstname");
            lastName = resultSet.getString("lastname");
            userName = resultSet.getString("username");
            dob = resultSet.getString("dob");
            age = resultSet.getInt("age");
            gender = resultSet.getString("gender");
            date = resultSet.getTimestamp("datecreated");
            inputStream=resultSet.getBinaryStream("adminphoto");
            c++;
        }

        if(c>0){
            detailsNmae.setText(firstName+" "+lastName);
            detailsUsername.setText(userName);
            detailsGender.setText(gender);
            detailsAge.setText(String.valueOf(age));
            detailsDob.setText(dob);
            detailsLastupdate.setText("Last Updated - "+date.toString());
            adminPhoto.setImage(new Image(inputStream));
        }

    }

    private void adminLogOut() throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/fontpagesample.fxml"));
        loader.load();

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        adminLogOutButton.getScene().getWindow().hide();


    }
}
