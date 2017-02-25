package main.com.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import main.com.utils.ConnectionManager;

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
    public Response getAvailablity(@HeaderParam("uid") String uid) {
        
        final Gson gson = new Gson();
        List<RoomStatus> list = new ArrayList<RoomStatus>();
        list.add(new RoomStatus("Pushya", "Booked"));
        list.add(new RoomStatus("Anuradha", "Booked"));
        list.add(new RoomStatus("Kritika", "Available"));
        final String gsonStr = gson.toJson(list);
        
        ResponseBuilder response = Response.ok(gsonStr).status(200);
        return response.build(); 
    	
    
    }
    
    @GET
    @Path("/RoomStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getStatus(@HeaderParam("roomName") String RoomName) {
        //For MongoDb Connection And status
    	ConnectionManager conman= new ConnectionManager();
    	Map<String, String> VRStatus= new HashMap<String, String>();
		VRStatus = conman.GetMappedStatusFromVR();
		System.out.println(VRStatus);
        return VRStatus;
    }
    
    @GET
    @Path("/AdhocStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdhocStatus(@HeaderParam("roomName") String RoomName) {
    	
    	// Make a call to mongoDB
    	
    	// Make a call to Node URL
    	
    	
        final Gson gson = new Gson();
        
        List<RoomStatus> list = new ArrayList<RoomStatus>();
        list.add(new RoomStatus("Pushya", "AO"));
        list.add(new RoomStatus("Anuradha", "AE"));
        list.add(new RoomStatus("Kritika", "BE"));
        list.add(new RoomStatus("Mars", "BE"));
        list.add(new RoomStatus("Rohini", "BE"));
        final String gsonStr = gson.toJson(list);
        
        ResponseBuilder response = Response.ok(gsonStr).status(200);
        return response.build(); 
    }
}
