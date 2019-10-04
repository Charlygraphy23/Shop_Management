package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DBHandller;
import sample.model.Admin;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDetailsConfirmation {

    @FXML
    private Label loginuserLabel;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton loginButton;

    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        loginButton.setOnAction(a->{
            try {
                getSignUpScreen();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void getSignUpScreen() throws SQLException, ClassNotFoundException, IOException {
        if(!username.getText().equals("") && !password.getText().equals("")){
            Admin admin=new Admin();
            admin.setAdminUsername(username.getText());
            admin.setAdminPassword(password.getText());

            DBHandller handller=new DBHandller();
            ResultSet resultSet=handller.adminDetailsCheck(admin);

            int c=0;
            while (resultSet.next()){
                c++;
            }
            if(c>0){
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/adminregister.fxml"));
                loader.load();

                Parent root=loader.getRoot();
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();

                loginButton.getScene().getWindow().hide();
            }
            else {
                Alert alert=new Alert(Alert.AlertType.ERROR,"Username and Password Invalid");
                alert.setWidth(70);
                alert.setHeight(38);
                alert.show();
            }
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Please Enter Valid Details");
            alert.setWidth(70);
            alert.setHeight(38);
            alert.show();
        }
    }

}
