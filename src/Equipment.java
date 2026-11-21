public abstract class Equipment {
    private String equipmentID;
    private String equipmentName;
    private int totalQuantity;
    private int availableQuantity;

    Equipment(String equipmentID, String equipmentName, int totalQuantity, int availableQuantity) {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

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
}
