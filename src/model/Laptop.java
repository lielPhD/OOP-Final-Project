package model;

public class Laptop extends Equipment{
    private String operatingSystem;
    private String model;

    public Laptop(
            String equipmentID,
            String equipmentName,
            int totalQuantity,
            int availableQuantity,
            String operatingSystem,
            String model){

        super(equipmentID, equipmentName, totalQuantity, availableQuantity);
        this.operatingSystem = operatingSystem;
        this.model = model;
    }

    public String getOperatingSystem(){
        return operatingSystem;
    }

    public String getModel(){
        return model;
    }

    @Override
    public String getEquipmentType(){
        return "Laptop";
    }
}
