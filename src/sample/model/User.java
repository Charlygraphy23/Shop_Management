package sample.model;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private InputStream photo;
    private String gender;
    private String dob;
    private int age;
    private String mobNo;
    private String address;
    private String shopId;
    private String openDate;
    private Timestamp lastUpdatedDetails;
    private Date alermDate;

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public Timestamp getLastUpdatedDetails() {
        return lastUpdatedDetails;
    }

    public void setLastUpdatedDetails(Timestamp lastUpdatedDetails) {
        this.lastUpdatedDetails = lastUpdatedDetails;
    }


    public Date getAlermDate() {
        return alermDate;
    }

    public void setAlermDate(Date alermDate) {
        this.alermDate = alermDate;
    }
}
