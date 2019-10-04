package sample.controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.model.Transaction;

import java.io.IOException;

public class CellTransaction extends JFXListCell<Transaction> {

    @FXML
    private Label lastPayment;

    @FXML
    private Label dues;

    @FXML
    private Label advance;

    @FXML
    private Label lastDate;

    @FXML
    private Label pajyDate;

    @FXML
    private Label id;

    @FXML
    private AnchorPane cellPane;

    private FXMLLoader loader;


    @FXML
    void initialize() {

    }

    @Override
    protected void updateItem(Transaction transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if (empty || transaction == null) {
            setGraphic(null);
            setText(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/sample/view/celltransaction.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                lastPayment.setText(String.valueOf("Last Ammount "+transaction.getTotalAmount())+" rs/-");
                dues.setText("Dues "+String.valueOf(transaction.getDue()));
                advance.setText("Advance "+String.valueOf(transaction.getAdvance()));
                lastDate.setText("Last Date Of Transaction "+String.valueOf(transaction.getTransactionDate()));
                id.setText(String.valueOf("Id "+transaction.getTransId()));
                pajyDate.setText("Pay Date "+transaction.getPayDate());


                setText(null);
                setGraphic(cellPane);

            }
        }
    }


}
