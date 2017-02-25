package main.com.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import org.codehaus.jackson.annotate.JsonRawValue;

import main.com.services.data.RoomConstants;
import main.com.services.data.RoomStatus;
import main.com.services.data.RoomSummary;
import main.com.utils.ConnectionManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Room")
public class RoomLocatorService {
	

	@GET
	@Path("/Meeting")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllGalaxies(@HeaderParam("uid") String uid) {
		
		return "{message:\"This response is from server\"}";
	}
	
    @POST
    @Path("/Availablity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailablity(JsonObject uid) {
        System.out.println("Uid is:"+uid);
        final Gson gson = new Gson();
        List<RoomStatus> list = new ArrayList<RoomStatus>();
        list.add(new RoomStatus(RoomConstants.PUSHYA, "Booked"));
        list.add(new RoomStatus(RoomConstants.ANURADHA, "Booked"));
        list.add(new RoomStatus(RoomConstants.KRITIKA, "Available"));
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
    	RoomSummary roomSummary = new RoomSummary();
    	try {
			// Make a call to mongoDB
    		String occupancyJson = getRoomOccupancyJson();
    		Map<String, String> occupancyMap = parseJsonResponseOccupancy(occupancyJson);
			
			// Make a call to Node URL
			String responseJson = sendPostGoogleAPI();
			List<RoomStatus> listRoomStatus = parseJsonResponseFromGoogleAPI(responseJson);
			for (RoomStatus roomStatus : listRoomStatus) {
				roomSummary.addRoomStatus(roomStatus.getRoomName(), manipulatedStatus(roomStatus.getRoomStatus(), occupancyMap.get(roomStatus.getRoomName())));
			}
		} catch (Exception e) {
			System.out.println("Exception:"+e.getMessage());
	        List<RoomStatus> list = new ArrayList<RoomStatus>();
	        list.add(new RoomStatus(RoomConstants.PUSHYA, getRandomStatus(RoomConstants.PUSHYA)));
	        list.add(new RoomStatus(RoomConstants.ANURADHA, getRandomStatus(RoomConstants.ANURADHA)));
	        list.add(new RoomStatus(RoomConstants.KRITIKA, RoomStatus.AE));
	        list.add(new RoomStatus(RoomConstants.MARS,RoomStatus. BO));
	        list.add(new RoomStatus(RoomConstants.ROHINI, RoomStatus.BE));
	        
	        roomSummary.setRoomAvailability(list);
		}
    	
        final Gson gson = new Gson();
        final String gsonStr = gson.toJson(roomSummary);
        
        ResponseBuilder response = Response.ok(gsonStr).status(200);
        return response.build(); 
    }

	private String manipulatedStatus(String bookingStatus, String occupancy) {
		if(bookingStatus.equalsIgnoreCase("available")){
			if(occupancy.equalsIgnoreCase("empty")){
				return RoomStatus.AE;
			} else {
				return RoomStatus.AO;
			}
		} else {
			if(occupancy.equalsIgnoreCase("empty")){
				return RoomStatus.BE;
			} else {
				return RoomStatus.BO;
			}
			
		}
	}

	private String getRandomStatus(String pushya) {
		int random = (int) (Math.random()*4);
		return ((random<2)?RoomStatus.AE:((random<3)?RoomStatus.AO:RoomStatus.BO));
	}
	
	private String sendPostGoogleAPI() throws Exception {

		OkHttpClient client = new OkHttpClient();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String startDate = simpleDateFormat.format(new Date())+"+5:30";
		String endDate = simpleDateFormat.format(new Date(new Date().getTime()+300000))+"+5:30";

		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n\"startDateTime\":\""+startDate+"\",\n\"endDateTime\":\""+endDate+"\"\n}");
		Request request = new Request.Builder()
		  .url("http://10.222.120.163:3001/getAllRoomsStatus")//ngpr16:3001/getAllRoomsStatus")
		  .post(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "2738f157-b458-ade3-216e-dabd91664a56")
		  .build();

		okhttp3.Response response = client.newCall(request).execute();


		//print result
		ResponseBody responseBody = response.body();
		String responseJson = responseBody.source().readUtf8();
		
		return responseJson;
	}
	
	private String sendPostOccupiAPI() throws Exception {

		OkHttpClient client = new OkHttpClient();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String startDate = simpleDateFormat.format(new Date())+"+05:30";
		String endDate = simpleDateFormat.format(new Date(new Date().getTime()+300000))+"+05:30";

		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n\"startDateTime\":\""+startDate+"\",\n\"endDateTime\":\""+endDate+"\"\n}");
		Request request = new Request.Builder()
		  .url("http://localhost:8080/Semicolon_Project/rest/imageClassification/classify")
		  .post(body)
		  .addHeader("content-type", "application/json")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "2738f157-b458-ade3-216e-dabd91664a56")
		  .build();

		okhttp3.Response response = client.newCall(request).execute();


		//print result
		ResponseBody responseBody = response.body();
		String responseJson = responseBody.source().readUtf8();
		
		return responseJson;
	}
	
	private List<RoomStatus> parseJsonResponseFromGoogleAPI(String jsonStr) {
		List<RoomStatus> list = new ArrayList<RoomStatus>();
		JsonElement jsonElement = new JsonParser().parse(jsonStr);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject jsonResult = jsonObject.get("result").getAsJsonObject();
		JsonElement jsonElementStatus = jsonResult.get("roomStatus");
		if(jsonElementStatus.isJsonArray()){
			JsonArray resultArray = jsonElementStatus.getAsJsonArray();
			for (JsonElement roomStatusElement : resultArray) {
				JsonObject roomStatusObj = roomStatusElement.getAsJsonObject();
				RoomStatus roomStatus = new RoomStatus(roomStatusObj.get("roomName").toString(), roomStatusObj.get("roomStatus").toString());
				list.add(roomStatus);
			}
		}
		return list;
	}
	
	private final String GET_URL = "http://10.222.120.147:8080/Semicolon_Project/rest/imageClassification/classify";

	private String getRoomOccupancyJson() throws Exception {
		String url = GET_URL;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("Get");

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	
	private Map<String, String> parseJsonResponseOccupancy(String jsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		JsonElement jsonElement = new JsonParser().parse(jsonStr);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonObject jsonResult = jsonObject.get("status").getAsJsonObject();
		if(jsonResult.isJsonArray()){
			JsonArray resultArray = jsonResult.getAsJsonArray();
			for (JsonElement roomStatusElement : resultArray) {
				JsonObject roomStatusObj = roomStatusElement.getAsJsonObject();
				map.put(roomStatusObj.get("RoomName").toString(), roomStatusObj.get("RoomOccupancyStatus").toString());
			}
		}
		return map;
	}
}
