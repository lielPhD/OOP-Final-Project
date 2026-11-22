package model;

public abstract class Equipment {
    private String equipmentID;
    private String equipmentName;
    private int totalQuantity;
    private int availableQuantity;

    protected Equipment (
            String equipmentID,
            String equipmentName,
            int totalQuantity,
            int availableQuantity) {

        //prevents entering higher available quantity than total quantity, and prevents negative quantities
        if (totalQuantity < 0 || availableQuantity < 0 ||
                availableQuantity > totalQuantity) {
            throw new IllegalArgumentException(
                    "Invalid equipment quantity."
            );
        }

        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getEquipmentID() { return equipmentID; }

    public String getEquipmentName() {
        return equipmentName;
    }

    public abstract String getEquipmentType();

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void borrowEquipment(int quantity) {
        if (quantity <= 0 || quantity > availableQuantity) {
            throw new IllegalArgumentException(
                    "Insufficient available equipment."
            );
        }

        availableQuantity -= quantity;
    }

    public void returnEquipment(int quantity) {
        if (quantity <= 0 ||
                availableQuantity + quantity > totalQuantity) {

            throw new IllegalArgumentException(
                    "Invalid return quantity."
            );
        }

        availableQuantity += quantity;
    }

    public void updateDetails(String newName, int newTotalQuantity) {

        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException(
                    "Equipment name cannot be empty."
            );
        }

        if (newTotalQuantity < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative."
            );
        }

        int borrowedQuantity = totalQuantity - availableQuantity;

        if (newTotalQuantity < borrowedQuantity) {
            throw new IllegalArgumentException(
                    "New quantity cannot be less than the borrowed quantity."
            );
        }

        this.equipmentName = newName.trim();
        this.totalQuantity = newTotalQuantity;
        this.availableQuantity = newTotalQuantity - borrowedQuantity;
    }
}
