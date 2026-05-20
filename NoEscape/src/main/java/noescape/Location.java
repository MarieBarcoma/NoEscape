package noescape;

public class Location {

    private final String buildingName;
    private final Room room;

    public Location(String buildingName, Room room) {
        this.buildingName = buildingName;
        this.room = room;
    }

    public String getBuildingName() {
        return buildingName; 
    }
    public Room getRoom() {
        return room;         
    }

    @Override
    public String toString() {
        return buildingName + " - " + room.getName();
    }
}