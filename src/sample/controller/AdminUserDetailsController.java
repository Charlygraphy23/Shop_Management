package sample.controller;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DBuserController;
import sample.model.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserDetailsController {

    @FXML
    private JFXListView<User> userList;

    private ObservableList<User> list;

    @FXML
    void close(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/admindetails.fxml"));
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
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void initialize() {

        list= FXCollections.observableArrayList();
        DBuserController controller=new DBuserController();

        try {
            ResultSet resultSet=controller.getUserr();

            while (resultSet.next()){
                User user=new User();
                user.setUserId(resultSet.getInt("userid"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setUserName(resultSet.getString("username"));
                user.setPhoto(resultSet.getBinaryStream("photo"));
                user.setGender(resultSet.getString("gender"));
                user.setDob(resultSet.getString("dob"));
                user.setMobNo(resultSet.getString("mobno"));
                user.setAddress(resultSet.getString("address"));
                user.setShopId(resultSet.getString("shopid"));
                user.setOpenDate(resultSet.getString("openningdate"));
                user.setLastUpdatedDetails(resultSet.getTimestamp("lastupdateddate"));
                list.addAll(user);

            }

            userList.setItems(list);
            userList.setCellFactory(CellUserDetailsController -> new CellUserDetailsController());


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
