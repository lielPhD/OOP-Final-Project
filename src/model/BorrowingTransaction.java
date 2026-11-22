package model;

import java.time.LocalDate;

public class BorrowingTransaction {

    private String transactionId;
    private User borrower;
    private Equipment equipment;
    private int quantity;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;


    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }


}
