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
    private TransactionStatus status;

    public BorrowingTransaction(String transactionID, User borrower, Equipment equipment, int quantity, LocalDate borrowDate, LocalDate dueDate) {

        if(transactionID == null ||
                transactionID.isBlank() ||
                borrower == null ||
                equipment == null ||
                quantity <= 0 ||
                borrowDate == null ||
                dueDate == null ||
                dueDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("Invalid input");
        }

        this.transactionId = transactionID;
        this.borrower = borrower;
        this.equipment = equipment;
        this.quantity = quantity;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.status = TransactionStatus.BORROWED;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public User getBorrower() {
        return borrower;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void markAsReturned() {
        if (status == TransactionStatus.RETURNED) {
            throw new IllegalStateException(
                    "This equipment has already been returned."
            );
        }
        this.returnDate = LocalDate.now();
        this.status = TransactionStatus.RETURNED;
    }

    public TransactionStatus getStatus() {
        if (status == TransactionStatus.BORROWED && LocalDate.now().isAfter(dueDate)) {
            return TransactionStatus.OVERDUE;
        }
        return status;
    }
}
