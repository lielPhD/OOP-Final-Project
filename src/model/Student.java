package model;

public class Student extends User{
    private String course;
    private String yearLevel;

    public Student(String userID, String userName, String userEmail, String userPassword, String course, String yearLevel) {
        super(userID, userName, userEmail, userPassword);
        this.course = course;
        this.yearLevel = yearLevel;
    }

    public String getCourse() {
        return course;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    @Override
    public String getUserType(){
        return "model.Student";
    }

    @Override
    public boolean canManageEquipment(){
        return false;
    }
}
