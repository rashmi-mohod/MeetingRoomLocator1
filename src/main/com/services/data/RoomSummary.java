package main.com.services.data;

import java.util.ArrayList;
import java.util.List;

public class RoomSummary {

	private List<RoomStatus> roomAvailability=new ArrayList<RoomStatus>();
	
	public void addRoomStatus(RoomStatus roomStatus){
		roomAvailability.add(roomStatus);
	}

	public List<RoomStatus> getRoomAvailability() {
		return roomAvailability;
	}

	public void setRoomAvailability(List<RoomStatus> roomAvailability) {
		this.roomAvailability = roomAvailability;
	}
	
	
}
