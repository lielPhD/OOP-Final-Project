package service;

import model.Equipment;
import model.User;
import java.util.ArrayList;

public class EquipmentService {

    private ArrayList<Equipment> equipmentList;

    public EquipmentService() {
        equipmentList = new ArrayList<>();
    }

    public ArrayList<Equipment> getAllEquipment() {
        return new ArrayList<>(equipmentList);
    }

    public ArrayList<Equipment> getAvailableEquipment() {

        ArrayList<Equipment> availableList = new ArrayList<>();

        for (Equipment equipment : equipmentList) {

            if (equipment.getAvailableQuantity() > 0) {
                availableList.add(equipment);
            }
        }

        return availableList;
    }

    public ArrayList<Equipment> getOutOfStockEquipment() {

        ArrayList<Equipment> outOfStockList = new ArrayList<>();

        for (Equipment equipment : equipmentList) {

            if (equipment.getAvailableQuantity() == 0) {
                outOfStockList.add(equipment);
            }
        }

        return outOfStockList;
    }

    private void checkFacultyPermission(User user) {
        if (user == null || !user.canManageEquipment()) {
            throw new SecurityException(
                    "Only faculty members can manage equipment."
            );
        }
    }

    public void addEquipment(Equipment equipment, User user) {

        checkFacultyPermission(user);

        if (equipment == null) {
            throw new IllegalArgumentException("Equipment cannot be empty.");
        }

        if (findEquipmentByID(equipment.getEquipmentID()) != null) {
            throw new IllegalArgumentException(
                    "Equipment ID already exists."
            );
        }

        equipmentList.add(equipment);
    }

    public Equipment findEquipmentByID(String equipmentID) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getEquipmentID().equalsIgnoreCase(equipmentID)) {
                return equipment;
            }
        }
        return null;
    }

    public void removeEquipment(String equipmentID, User user) {

        checkFacultyPermission(user);

        Equipment equipment = findEquipmentByID(equipmentID);

        if (equipment == null) {
            throw new IllegalArgumentException(
                    "Equipment not found."
            );
        }

        if (equipment.getAvailableQuantity() != equipment.getTotalQuantity()) {
            throw new IllegalStateException(
                    "Cannot remove equipment with active borrowings."
            );
        }

        equipmentList.remove(equipment);
    }

    public void updateEquipment(
            String equipmentID,
            String newName,
            int newTotalQuantity,
            User user) {

        checkFacultyPermission(user);

        Equipment equipment = findEquipmentByID(equipmentID);

        if (equipment == null) {
            throw new IllegalArgumentException(
                    "Equipment not found."
            );
        }

        equipment.updateDetails(newName, newTotalQuantity);
    }

    public ArrayList<Equipment> searchEquipment(String keyword) {

        ArrayList<Equipment> searchResults = new ArrayList<>();

        if (keyword == null || keyword.isBlank()) {
            return searchResults;
        }

        for (Equipment equipment : equipmentList) {

            if (equipment.getEquipmentID().toLowerCase()
                    .contains(keyword.trim().toLowerCase())
                    || equipment.getEquipmentName().toLowerCase()
                    .contains(keyword.trim().toLowerCase())) {

                searchResults.add(equipment);
            }
        }

        return searchResults;
    }
}