package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DBuserController;
import sample.model.Transaction;
import sample.model.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class PayController {

    @FXML
    private Label totalAmmt;

    @FXML
    private JFXTextField payAmmountText;

    @FXML
    private Label dues;

    @FXML
    private Label advance;

    @FXML
    private Label nextmonthPay;

    @FXML
    private JFXButton payButton;

    @FXML
    private JFXDatePicker payDate;
    int totalAmount=1500;
    private int updateAmmount=0,count=0;
    private int due=0,advace=0,getAmmount=0;
    private LocalDate now;
    private int day;
    private Transaction transaction;


    private int dbDues,dbAdvance;

    @FXML
    void close(MouseEvent event) {

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
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void initialize() {

        DBuserController controller=new DBuserController();

        try {
            ResultSet resultSet=controller.findLastTransaction();
            while (resultSet.next()){
                dbDues=resultSet.getInt("due");
                dbAdvance=resultSet.getInt("advance");
                updateAmmount=resultSet.getInt("totalammount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("Dues "+ dbDues);
        System.out.println("Advance "+dbAdvance);
        System.out.println("Total Ammount "+updateAmmount);


        dues.setOnMouseClicked(m->{
            getAmmount=Integer.parseInt(payAmmountText.getText());
            due=0;
            advace=0;
            if(updateAmmount==0){
                if (getAmmount<totalAmount){
                    due=totalAmount-getAmmount;
                    dues.setText("Dues "+due);
                    advance.setText("No Advance");
                }
                else if(getAmmount>totalAmount){
                    advace=getAmmount-totalAmount;
                    advance.setText("Advance "+advace);
                    dues.setText("No Dues");
                }
                else {
                    due=0;
                    advace=0;
                    dues.setText("No Dues ");
                    advance.setText("No Advance");

                }

            }
            else if(updateAmmount!=0){
                if (getAmmount<(totalAmount+dbDues) && dbAdvance==0){
                    due=totalAmount-getAmmount;
                    due=due+dbDues;
                    dues.setText("Dues "+due);
                    advance.setText("No Advance");
                }
                else if(getAmmount>(totalAmount+dbDues-dbAdvance)){
                    advace=Math.abs(getAmmount-totalAmount);
                    if(dbAdvance==0){
                        advace=advace-dbDues;
                    }
                    else if(dbAdvance!=0)
                    {
                        advace=advace+dbAdvance;
                    }
                    advance.setText("Advance "+advace);
                    dues.setText("No Dues");
                }else if(getAmmount==(totalAmount+dbDues)) {
                    due=0;
                    advace=0;
                    dbDues=0;
                    dbAdvance=0;
                    dues.setText("No Dues ");
                    advance.setText("No Advance");

                }

                else if(getAmmount<(totalAmount+dbDues) && dbAdvance!=0){
                    int sum=Math.abs(getAmmount-totalAmount);
                    sum=dbAdvance-sum;
                    System.out.println(sum);
                    if(dbAdvance!=0){
                        if(sum<0){
                            due=Math.abs(sum);
                            advace=0;
                            dues.setText("Dues "+due);
                            advance.setText("No Advance ");
                        }

                        else {
                            advace=sum;
                            dues.setText("No Dues ");
                            advance.setText("Advance "+advace);
                        }
                    }



                }

            }

            if(due!=0)
            nextmonthPay.setText("** Total Ammount For Next Mounth- "+(totalAmount+due));
            else if(advace!=0){
                nextmonthPay.setText("** Total Ammount For Next Mounth- "+(totalAmount-advace));
            }
            else {
                nextmonthPay.setText("** Total Ammount For Next Mounth- "+(totalAmount));
            }
        });

        if(dbDues!=0){
            totalAmmt.setText("** Total Ammount - "+(totalAmount+dbDues));
        }
        else if(dbAdvance!=0){
            totalAmmt.setText("** Total Ammount - "+(totalAmount-dbAdvance));
        }
        else {
            totalAmmt.setText("** Total Ammount - "+totalAmount);
        }


        payButton.setOnAction(a->{
            System.out.println("Dues "+due);
            System.out.println("Advance "+advace);
           //Timestamp timestamp=new Timestamp(Calendar.getInstance().getWeekYear());
            Date date=new Date(Calendar.getInstance().getWeekYear());
            System.out.println("Total mmount "+totalAmount);

            if(payDate.getValue() == null){
                Alert alert=new Alert(Alert.AlertType.ERROR,"Please Enter a Date");
                alert.show();
            }



            now = LocalDate.now();
            LocalDate openDate = payDate.getValue();

            Period p = Period.between(now, openDate);
            int days = Math.abs(p.getDays());
            int months = Math.abs(p.getMonths());
            if (months > 0) {
                day = (months * 30) + days;
                System.out.println("Days " + days);
            }
            else {
                day=openDate.getDayOfMonth();
            }

            if(payAmmountText.getText()!= null && payDate.getValue()!=null && day>now.getDayOfMonth() && day>30){
                User user=new User();
                user.setAlermDate(date);
                transaction=new Transaction();
                transaction.setDue(due);
                transaction.setAdvance(advace);
                transaction.setPayDate(String.valueOf(openDate));
                transaction.setTransactionDate(date);
                transaction.setTotalAmount(getAmmount);

                try {
                    controller.addTransaction(transaction);
                    controller.updateAlermDate(user);

                    Alert alert=new Alert(Alert.AlertType.INFORMATION,"Insert Successfully");
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
                            payButton.getScene().getWindow().hide();
                        }
                    });
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            else {
                Alert alert=new Alert(Alert.AlertType.ERROR,"Invalid Details");
                alert.show();
            }


        });

    }
}
