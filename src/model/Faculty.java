package model;

public class Faculty extends User{
    private String department;

    public Faculty(
            String userID,
            String userName,
            String userEmail,
            String userPassword,
            String department) {

        super(userID, userName, userEmail, userPassword);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getUserType(){
        return "Faculty";
    }

    @Override
    public boolean canManageEquipment(){
        return true;
    }
}
