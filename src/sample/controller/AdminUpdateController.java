package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.database.DBHandller;
import sample.model.Admin;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

public class AdminUpdateController extends Window {


    @FXML
    private JFXTextField updateFirstname;

    @FXML
    private JFXTextField updateLastName;

    @FXML
    private JFXTextField updateUsername;

    @FXML
    private JFXComboBox<String> updateGenderCombo;

    @FXML
    private JFXDatePicker updateDob;

    @FXML
    private JFXTextField updateAgelabel;

    @FXML
    private ImageView updatePhoto;

    @FXML
    private JFXButton updateButton;

    @FXML
    private JFXButton canclebutton;

    @FXML
    private ImageView uploadButton;

    private ObservableList<String> list= FXCollections.observableArrayList("Male","Female","Other");
    private InputStream inputStream=null;
    private File file;
    private FileInputStream fileInputStream;
    private int adminAge=0;
    private boolean isClicked=false;

    @FXML
    void close(MouseEvent event) {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }



    @FXML
    void initialize(String firstName, String lastName, String userName, String gender, String dob, int age,String pass) throws SQLException, ClassNotFoundException {



        DBHandller handller=new DBHandller();

        ResultSet resultSet=handller.grabImage();

        while (resultSet.next()){
            inputStream=resultSet.getBinaryStream("adminphoto");
        }

       updateGenderCombo.setItems(list);
       updateFirstname.setText(firstName);
       updateLastName.setText(lastName);
       updateUsername.setText(userName);
       updateGenderCombo.getSelectionModel().select(gender);
       updateDob.setValue(LocalDate.parse(dob));
       updatePhoto.setImage(new Image(inputStream));
       updateAgelabel.setText(String.valueOf(age));


       uploadButton.setOnMouseClicked(m->{
           getPhoto();
       });

       updateButton.setOnAction(a->{
           try {

               updateDetails();

           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (SQLException e) {
               e.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       });


            updateDob.setOnAction(a->{
                if(updateDob.getValue()!=null) {
                    adminAge = Calendar.getInstance().getWeekYear() - updateDob.getValue().getYear();
                    updateAgelabel.setText(Integer.toString(adminAge));
                }
                else {
                    updateAgelabel.setText("");
                    Alert alert=new Alert(Alert.AlertType.ERROR,"Enter valid age");
                    alert.setWidth(70);
                    alert.setHeight(38);
                    alert.show();
                }

            });


    }




    private void updateDetails() throws IOException, SQLException, ClassNotFoundException {


        Timestamp timestamp=new Timestamp(Calendar.getInstance().getTimeInMillis());

        if(!updateFirstname.getText().equals("") && !updateLastName.getText().equals("") && !updateUsername.getText().equals("") && updateGenderCombo.getSelectionModel().getSelectedItem()!=null && !updateAgelabel.getText().equals("")
        && updateDob.getValue()!=null){
            DBHandller dbHandller=new DBHandller();
            Admin admin=new Admin();
            admin.setAdminFirstname(updateFirstname.getText().toUpperCase());
            admin.setAdminLastname(updateLastName.getText().toUpperCase());
            admin.setAdminUsername(updateUsername.getText());
            admin.setAdminGender(updateGenderCombo.getSelectionModel().getSelectedItem());
            admin.setAdminAge(Integer.parseInt(updateAgelabel.getText()));
           if(updateDob.getValue() == null){
               Alert alert=new Alert(Alert.AlertType.ERROR,"Invalid user details");
               alert.setWidth(70);
               alert.setHeight(38);
               alert.show();
           }
           else {
               admin.setAdminDOB(updateDob.getValue().toString());
           }
            admin.setDateCreated(timestamp);

            if(file==null){
                ResultSet resultSet=dbHandller.grabImage();
                while (resultSet.next()){
                    inputStream=resultSet.getBinaryStream("adminphoto");
                }
                admin.setPhoto(inputStream);
                System.out.println("File is Null ");
            }
            else {
                fileInputStream=new FileInputStream(file.getAbsoluteFile());
                System.out.println("File has a path");
                admin.setPhoto(fileInputStream);
            }



            dbHandller.updateAdmin(admin);


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/admindetails.fxml"));
            loader.load();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            updateButton.getScene().getWindow().hide();


        }
        else {
            System.out.println("Error");
        }

    }

    private void getPhoto() {
        FileChooser fileChooser=new FileChooser();
        file=fileChooser.showOpenDialog(this);
        if(file!=null){
            Image image=new Image(file.toURI().toString());
            updatePhoto.setImage(image);
        }
    }
}
