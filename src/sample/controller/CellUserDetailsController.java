package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sample.model.User;

import java.awt.*;
import java.io.IOException;

public class CellUserDetailsController extends JFXListCell<User> {

    @FXML
    private AnchorPane userPane;

    @FXML
    private ImageView userImage;

    @FXML
    private Label userId;

    @FXML
    private Label name;

    @FXML
    private Label userName;

    @FXML
    private Label gender;

    @FXML
    private Label dob;

    @FXML
    private Label mobNo;

    @FXML
    private Label address;

    @FXML
    private Label shopId;

    @FXML
    private Label openningDate;

    @FXML
    private Label lastUpdateddate;

    private FXMLLoader loader;


    @FXML
    void initialize(){
    }

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if(empty || user==null){
            setText(null);
            setGraphic(null);
        }

        else {
            if(loader==null){
                loader=new FXMLLoader(getClass().getResource("/sample/view/celluserdetails.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                userId.setText("Id "+String.valueOf(user.getUserId()));
                name.setText("Name "+user.getFirstName()+" "+user.getLastName());
                userName.setText("Username "+user.getUserName());
                gender.setText("Gender "+user.getGender());
                dob.setText("Dob "+user.getDob());
                mobNo.setText("Mob No "+user.getMobNo());
                address.setText("Address "+user.getAddress());
                shopId.setText("Shop Id "+user.getShopId());
                lastUpdateddate.setText("Last Update "+String.valueOf(user.getLastUpdatedDetails()));
                openningDate.setText("Openning Date "+String.valueOf(user.getOpenDate()));
                userImage.setImage(new Image(user.getPhoto()));




                setText(null);
                setGraphic(userPane);


            }
        }

    }
}
