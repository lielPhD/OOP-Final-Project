package model;

public abstract class User {
    private String userID;
    private String userName;
    private String userEmail;
    private String userPassword;

    protected User(
            String userID,
            String userName,
            String userEmail,
            String userPassword) {

        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
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
