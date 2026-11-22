package model;

public class Projector extends Equipment{
    private String brand;
    private String model;

    public Projector(
            String equipmentID,
            String equipmentName,
            int totalQuantity,
            int availableQuantity,
            String brand,
            String model){

        super(equipmentID, equipmentName, totalQuantity, availableQuantity);
        this.brand = brand;
        this.model = model;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    @Override
    public String getEquipmentType(){
        return "Projector";
    }

}
