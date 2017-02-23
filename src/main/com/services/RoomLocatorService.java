package main.com.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Room")
public class RoomLocatorService {
	
	@GET
	@Path("/Meeting")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllGalaxies(@HeaderParam("uid") String uid) {
		
		return "{message:\"This response is from server\"}";
	}
	
    @GET
    @Path("/Availablity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAvailablity(@HeaderParam("uid") String uid) {
        
        return "{\"rooms\": {\"availability\": ["
                + "{\"Pushya\": \"Free\"},"
                + "{\"Anuradha\": \"Yellow\"},"
                + "{\"Kritika\": \"Voilet\"},"
                + "{\"Rohini\": \"Green\"},"
                + "{\"Mars\": \"Red\"}"
                + "]}}";
    }
    
    @GET
    @Path("/RoomStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatus(@HeaderParam("uid") String uid) {
        
        return "{\"rooms\": {\"roomStatus\": ["
                + "{\"Pushya\": \"Booked\"},"
                + "{\"Anuradha\": \"Booked\"},"
                + "{\"Kritika\": \"Available\"},"
                + "{\"Rohini\": \"Available\"},"
                + "{\"Mars\": \"Booked\"}"
                + "]}}";
    }
}
