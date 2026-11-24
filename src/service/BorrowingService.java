package service;

import model.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class BorrowingService {

    private ArrayList<BorrowingTransaction> transactions;
    private EquipmentService equipmentService;

    public BorrowingService(EquipmentService equipmentService) {
        transactions = new ArrayList<>();
        this.equipmentService = equipmentService;
    }

    private String generateTransactionID() {
        return String.format("TRX%03d", transactions.size() + 1);
    }

    public ArrayList<BorrowingTransaction> getCurrentTransactions() {

        ArrayList<BorrowingTransaction> currentTransactions =
                new ArrayList<>();

        for (BorrowingTransaction transaction : transactions) {

            if (transaction.getStatus() != TransactionStatus.RETURNED) {
                currentTransactions.add(transaction);
            }
        }

        return currentTransactions;
    }

    public ArrayList<BorrowingTransaction> getDueTodayTransactions() {

        ArrayList<BorrowingTransaction> dueTodayTransactions =
                new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (BorrowingTransaction transaction : transactions) {

            if (transaction.getStatus() != TransactionStatus.RETURNED
                    && transaction.getDueDate().equals(today)) {

                dueTodayTransactions.add(transaction);
            }
        }

        return dueTodayTransactions;
    }

    public ArrayList<BorrowingTransaction> getOverdueTransactions() {

        ArrayList<BorrowingTransaction> overdueTransactions =
                new ArrayList<>();

        for (BorrowingTransaction transaction : transactions) {

            if (transaction.getStatus() == TransactionStatus.OVERDUE) {
                overdueTransactions.add(transaction);
            }
        }

        return overdueTransactions;
    }

    public ArrayList<BorrowingTransaction> getBorrowingHistory() {
        return new ArrayList<>(transactions);
    }

    public BorrowingTransaction borrowEquipment(
            User borrower,
            String equipmentID,
            int quantity,
            int loanDays) {

        if (borrower == null) {
            throw new SecurityException("Please log in first.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Borrowing quantity must be greater than zero."
            );
        }

        if (loanDays <= 0) {
            throw new IllegalArgumentException(
                    "Loan duration must be greater than zero."
            );
        }

        Equipment equipment =
                equipmentService.findEquipmentByID(equipmentID);

        if (equipment == null) {
            throw new IllegalArgumentException(
                    "Equipment not found."
            );
        }

        if (quantity > equipment.getAvailableQuantity()) {
            throw new IllegalArgumentException(
                    "Not enough equipment available."
            );
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(loanDays);

        BorrowingTransaction transaction = new BorrowingTransaction(
                generateTransactionID(),
                borrower,
                equipment,
                quantity,
                borrowDate,
                dueDate
        );

        equipment.borrowEquipment(quantity);

        transactions.add(transaction);

        return transaction;
    }

    public void returnEquipment(String transactionID, User user) {

        if (user == null) {
            throw new SecurityException("Please log in first.");
        }

        BorrowingTransaction transaction =
                findTransactionByID(transactionID);

        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction not found."
            );
        }

        if (transaction.getStatus() == TransactionStatus.RETURNED) {
            throw new IllegalStateException(
                    "This equipment has already been returned."
            );
        }

        boolean isBorrower =
                transaction.getBorrower().getUserID()
                        .equals(user.getUserID());

        if (!isBorrower && !user.canManageEquipment()) {
            throw new SecurityException(
                    "You are not allowed to return this transaction."
            );
        }

        transaction.getEquipment().returnEquipment(
                transaction.getQuantity()
        );

        transaction.markAsReturned();
    }

    public BorrowingTransaction findTransactionByID(String transactionID) {

        if (transactionID == null) {
            return null;
        }

        for (BorrowingTransaction transaction : transactions) {

            if (transaction.getTransactionId()
                    .equalsIgnoreCase(transactionID.trim())) {

                return transaction;
            }
        }

        return null;
    }

    public ArrayList<BorrowingTransaction> searchTransactions(String keyword) {

        ArrayList<BorrowingTransaction> searchResults =
                new ArrayList<>();

        if (keyword == null || keyword.isBlank()) {
            return searchResults;
        }

        String searchKeyword = keyword.trim().toLowerCase();

        for (BorrowingTransaction transaction : transactions) {

            String transactionID =
                    transaction.getTransactionId().toLowerCase();

            String borrowerName =
                    transaction.getBorrower().getUserName().toLowerCase();

            String equipmentName =
                    transaction.getEquipment().getEquipmentName().toLowerCase();

            String equipmentID =
                    transaction.getEquipment().getEquipmentID().toLowerCase();

            if (transactionID.contains(searchKeyword)
                    || borrowerName.contains(searchKeyword)
                    || equipmentName.contains(searchKeyword)
                    || equipmentID.contains(searchKeyword)) {

                searchResults.add(transaction);
            }
        }

        return searchResults;
    }
}