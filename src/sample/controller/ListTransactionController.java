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
import sample.model.Transaction;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListTransactionController {


    @FXML
    private JFXListView<Transaction> listView;
    private ObservableList<Transaction> list;





    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        list= FXCollections.observableArrayList();
        DBuserController controller=new DBuserController();

        ResultSet resultSet=controller.getTransaction();

        while (resultSet.next()){
            Transaction transaction=new Transaction();
            transaction.setTransId(resultSet.getInt("transid"));
            transaction.setTotalAmount(resultSet.getInt("totalammount"));
            transaction.setTransactionDate(resultSet.getDate("transactiondate"));
            transaction.setPayDate(resultSet.getString("paydate"));
            transaction.setDue(resultSet.getInt("due"));
            transaction.setAdvance(resultSet.getInt("advance"));
            list.addAll(transaction);
        }

        listView.setItems(list);
        listView.setCellFactory(CellTransaction -> new CellTransaction());



    }
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

}
