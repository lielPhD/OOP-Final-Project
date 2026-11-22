package model;

public abstract class User {
    private String userID;
    private String userName;
    private String userEmail;
    private String userPassword;

    User(String borrowerID, String borrowerName, String borrowerEmail, String borrowerPassword) {
        this.userID = borrowerID;
        this.userName = borrowerName;
        this.userEmail = borrowerEmail;
        this.userPassword = borrowerPassword;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public abstract String getUserType();

    public abstract boolean canManageEquipment();

    public boolean validatePassword(String password) {
        return userPassword.equals(password);
    }
}
