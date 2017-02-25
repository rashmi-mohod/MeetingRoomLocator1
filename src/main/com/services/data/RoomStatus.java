package main.com.services.data;

public class RoomStatus {
    private String roomName;
    private String roomStatus;
	public static final String BO = "BO";
	public static final String AO = "AO";
	public static final String AE = "AE";
	public static final String BE = "BE";
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
