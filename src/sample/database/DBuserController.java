package sample.database;

import sample.controller.FrontPageController;
import sample.model.Admin;
import sample.model.Transaction;
import sample.model.User;

import javax.jws.soap.SOAPBinding;
import java.sql.*;

public class DBuserController {
    private Connection connection;
    private PreparedStatement preparedStatement;


    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/accountroom","root","root");
        System.out.println("Connection Established "+connection.getCatalog());


        return connection;
    }

    public ResultSet getUserLogin(User user) throws SQLException, ClassNotFoundException {

        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
        preparedStatement.setString(1,user.getUserName());
        preparedStatement.setString(2,user.getPassword());

        ResultSet resultSet=preparedStatement.executeQuery();

        return resultSet;

    }

    public ResultSet grabUserImage() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE userid=?");
        preparedStatement.setInt(1, FrontPageController.userId);
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void updateUser(User user) throws SQLException, ClassNotFoundException {
            preparedStatement = getConnection().prepareStatement("UPDATE users SET firstname=?,lastname=?,username=?,gender=?,age=?,dob=?,mobno=?,address=?,photo=?,lastupdateddate=? WHERE userid=?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getGender());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getDob());
            preparedStatement.setString(7, user.getMobNo());
            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setBinaryStream(9, user.getPhoto());
            preparedStatement.setTimestamp(10, user.getLastUpdatedDetails());
            preparedStatement.setInt(11,FrontPageController.userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }



        public void addTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {

            preparedStatement=getConnection().prepareStatement("INSERT INTO trans (transactiondate,due,advance,paydate,totalammount,userid) VALUES (?,?,?,?,?,?) ");
            preparedStatement.setDate(1,transaction.getTransactionDate());
            preparedStatement.setInt(2,transaction.getDue());
            preparedStatement.setInt(3,transaction.getAdvance());
            preparedStatement.setString(4,transaction.getPayDate());
            preparedStatement.setInt(5,transaction.getTotalAmount());
            preparedStatement.setInt(6,FrontPageController.userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }

        public ResultSet findLastTransaction() throws SQLException, ClassNotFoundException {
            preparedStatement = getConnection().prepareStatement("SELECT * FROM trans where userid=? ORDER BY transid DESC LIMIT 1");
            preparedStatement.setInt(1, FrontPageController.userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet;
        }

        public void updateAlermDate(User user) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE users SET alermdate=? WHERE userid=?");
        preparedStatement.setDate(1,user.getAlermDate());
        preparedStatement.setInt(2,FrontPageController.userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        }


        public ResultSet getUserDetails() throws SQLException, ClassNotFoundException {

        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE userid=?");
        preparedStatement.setInt(1,FrontPageController.userId);


        ResultSet resultSet=preparedStatement.executeQuery();

        return resultSet;

    }

    public ResultSet getTransaction() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM trans WHERE userid=?");
        preparedStatement.setInt(1,FrontPageController.userId);

        ResultSet resultSet=preparedStatement.executeQuery();

        return resultSet;
    }

    public ResultSet getAdminDetails() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM admins WHERE adminid=?");
        preparedStatement.setInt(1,FrontPageController.adminId);

        ResultSet resultSet=preparedStatement.executeQuery();

        return resultSet;
    }

    public ResultSet getUserr() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users");
        ResultSet resultSet=preparedStatement.executeQuery();

        return resultSet;
    }


}

