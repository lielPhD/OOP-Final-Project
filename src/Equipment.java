public class Equipment {

    private String equipmentID;
    private String equipmentName;

    Equipment(String equipmentID, String equipmentName) {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }
}
