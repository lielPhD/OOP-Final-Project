public class Borrowers {
    private String borrowerID;
    private String borrowerName;
    private String borrowerCategory;
    private String borrowerEmail;

    Borrowers(String borrowerID, String borrowerName, String borrowerCategory, String borrowerEmail) {
        this.borrowerID = borrowerID;
        this.borrowerName = borrowerName;
        this.borrowerCategory = borrowerCategory;
        this.borrowerEmail = borrowerEmail;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBorrowerCategory() {
        return borrowerCategory;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

}
