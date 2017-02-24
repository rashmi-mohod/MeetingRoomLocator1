package main.com.services;

public class RoomStatus {
    private String roomName;
    private String roomStatus;
    public RoomStatus(String roomName, String roomStatus) {
        super();
        this.roomName = roomName;
        this.roomStatus = roomStatus;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomStatus() {
        return roomStatus;
    }
    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
    
    
}
