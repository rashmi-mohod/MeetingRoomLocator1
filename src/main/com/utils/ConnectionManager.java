package main.com.utils;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class ConnectionManager {

	public Map<String, String> GetMappedStatusFromVR() {
		String status = "empty";
		String room = "Pushya";
		String occupancy = "RoomOccupancyStatus";
		String roomName = "RoomName";
		DBCursor dbCursor = null;
		List<DBObject> dbObjectList = null;
		Map<String, String> statusMap=new HashMap<String, String>();
		try {

			DBCollection collection = MongoUtils.getCollection();
			DBObject query = BasicDBObjectBuilder.start().get();
					dbCursor = collection.find(query);
					dbObjectList = dbCursor.toArray();
			 for (Iterator iterator = dbObjectList.iterator(); iterator
					.hasNext();) {
				DBObject dbObject = (DBObject) iterator.next();
				status = (dbObject.get(occupancy)).toString();
				room= (dbObject.get(roomName)).toString();
				statusMap.put(room, status);
				
			}
			 System.out.println(statusMap);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return statusMap;
	}
	
	
}
