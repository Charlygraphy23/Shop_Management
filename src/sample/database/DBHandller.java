package sample.database;

import sample.controller.FrontPageController;
import sample.model.Admin;
import sample.model.User;

import java.sql.*;

public class DBHandller {
    private Connection connection;
    private PreparedStatement preparedStatement;


    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/accountroom","root","root");
        System.out.println("Connection Established "+connection.getCatalog());


        return connection;
    }

    public int signUpAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM admins WHERE username=?");
        preparedStatement.setString(1,admin.getAdminUsername());

        ResultSet resultSet=preparedStatement.executeQuery();
        int c=0;
        while (resultSet.next()){
            c++;
        }

        if(c==0){
            preparedStatement=getConnection().prepareStatement("INSERT INTO admins(firstname,lastname,username,password,dob,age,gender,adminphoto,datecreated) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,admin.getAdminFirstname());
            preparedStatement.setString(2,admin.getAdminLastname());
            preparedStatement.setString(3,admin.getAdminUsername());
            preparedStatement.setString(4,admin.getAdminPassword());
            preparedStatement.setString(5,admin.getAdminDOB());
            preparedStatement.setInt(6,admin.getAdminAge());
            preparedStatement.setString(7,admin.getAdminGender());
            preparedStatement.setBinaryStream(8,admin.getPhoto());
            preparedStatement.setTimestamp(9,admin.getDateCreated());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return 0;
        }

        else return 1;

    }

    public int countAdmin() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT count(*) FROM admins");
        ResultSet resultSet=preparedStatement.executeQuery();


        while (resultSet.next()){
            return resultSet.getInt(1);
        }
       return resultSet.getInt(1);

    }

    public ResultSet adminDetailsCheck(Admin admin) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM admins WHERE username=? AND password=?");
        preparedStatement.setString(1,admin.getAdminUsername());
        preparedStatement.setString(2,admin.getAdminPassword());

        ResultSet resultSet=preparedStatement.executeQuery();

        return resultSet;
    }

    public void updateAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE admins SET firstname=?,lastname=?,username=?,gender=?,age=?,dob=?,adminphoto=?,datecreated=? WHERE adminid=?");
        preparedStatement.setString(1,admin.getAdminFirstname());
        preparedStatement.setString(2,admin.getAdminLastname());
        preparedStatement.setString(3,admin.getAdminUsername());
        preparedStatement.setString(4,admin.getAdminGender());
        preparedStatement.setInt(5,admin.getAdminAge());
        preparedStatement.setString(6,admin.getAdminDOB());
        preparedStatement.setBinaryStream(7,admin.getPhoto());
        preparedStatement.setTimestamp(8,admin.getDateCreated());
        preparedStatement.setInt(9, FrontPageController.adminId);
        preparedStatement.executeUpdate();
    }

    public ResultSet grabImage() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM admins WHERE adminid=?");
        preparedStatement.setInt(1,FrontPageController.adminId);
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public int getShopDetails(User user) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE shopid=?");
        preparedStatement.setString(1,user.getShopId());
        ResultSet resultSet=preparedStatement.executeQuery();
        int c=0;
        while (resultSet.next()){
            c++;
        }

        if(c>0){
            return 1;
        }
        else return 0;
    }

    public int signUpUser(User user) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE username=?");
        preparedStatement.setString(1,user.getUserName());

        ResultSet resultSet=preparedStatement.executeQuery();
        int c=0;
        while (resultSet.next()){
            c++;
        }

        if(c==0){
            preparedStatement=getConnection().prepareStatement("INSERT INTO users(firstname,lastname,username,password,photo,gender,dob,age,mobno,address,shopid,openningdate,lastupdateddate,alermdate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getUserName());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setBinaryStream(5,user.getPhoto());
            preparedStatement.setString(6,user.getGender());
            preparedStatement.setString(7,user.getDob());
            preparedStatement.setInt(8,user.getAge());
            preparedStatement.setString(9,user.getMobNo());
            preparedStatement.setString(10,user.getAddress());
            preparedStatement.setString(11,user.getShopId());
            preparedStatement.setString(12,user.getOpenDate());
            preparedStatement.setTimestamp(13,user.getLastUpdatedDetails());
            preparedStatement.setDate(14,user.getAlermDate());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return 0;
        }

        else return 1;

    }


}
