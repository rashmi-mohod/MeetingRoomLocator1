package main.com.src.main.com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.com.services.data.RoomStatus;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainClass {

	private final String USER_AGENT = "Mozilla/5.0";
	private final String GET_URL = "http://10.222.120.147:8080/Semicolon_Project/rest/imageClassification/classify";
	private final String POST_URL = "http://10.222.120.165:3001/getAllRoomsStatus";
	private final String GET_METHOD = "GET";
	private final String POST_METHOD = "POST";
	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
		ConnectionManager conman= new ConnectionManager();
		Map<String, String> VRStatus= new HashMap<String, String>();
		VRStatus = conman.GetMappedStatusFromVR();
		System.out.println(VRStatus);*/
		MainClass http = new MainClass();
		try {
	//		http.sendGet();
			System.out.println("\nTesting 2 - Send Http POST request");
			//http.sendPost();
			String jsonStr = "{  \"statusCode\": 200,  \"status\": true,  \"result\": {    \"kind\": \"calendar#freeBusy\",    \"timeMin\": \"2017-02-24T20:00:00.000Z\",    \"timeMax\": \"2017-02-25T17:25:00.000Z\",    \"roomStatus\": [      {        \"roomName\": \"Pushya\",        \"roomStatus\": \"Available\",        \"bookings\": {          \"busy\": []        }      },      {        \"roomName\": \"Anuradha\",        \"roomStatus\": \"Booked\",        \"bookings\": {          \"busy\": [            {              \"start\": \"2017-02-25T02:00:00+05:30\",              \"end\": \"2017-02-25T02:30:00+05:30\"            },            {              \"start\": \"2017-02-25T12:00:00+05:30\",              \"end\": \"2017-02-25T12:30:00+05:30\"            }          ]        }      },      {        \"roomName\": \"Kritika\",        \"roomStatus\": \"Available\",        \"bookings\": {          \"busy\": []        }      },      {        \"roomName\": \"Rohini\",        \"roomStatus\": \"Available\",        \"bookings\": {          \"busy\": []        }      },      {        \"roomName\": \"Mars\",        \"roomStatus\": \"Available\",        \"bookings\": {          \"busy\": []        }      }    ]  }}";
			//http.parseJsonResponse(jsonStr);
			String responseJson = http.sendPost();
			List<RoomStatus> listRoomStatus = http.parseJsonResponse(responseJson);
			for (RoomStatus roomStatus : listRoomStatus) {
				System.out.println(roomStatus.getRoomName()+":"+roomStatus.getRoomStatus());
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			}

	private List<RoomStatus> parseJsonResponse(String jsonStr) {
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

		// HTTP GET request
		private void sendGet() throws Exception {
				String url = GET_URL;

				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				con.setRequestMethod(GET_METHOD);

				//add request header
				con.setRequestProperty("User-Agent", USER_AGENT);

				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				//print result
				System.out.println(response.toString());

			}

			// HTTP POST request
/*			private void sendPost() throws Exception {

				
				String url = GET_URL;//POST_URL;
				URL obj = new URL(url);
				URLConnection openConnection = obj.openConnection();
				HttpURLConnection con = (HttpURLConnection) openConnection;

				//add reuqest header
				con.setRequestMethod(POST_METHOD);
				con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				con.setRequestProperty("Content-Type", "application/json");

				String urlParameters = "uid={\"startDateTime\":\"2017-02-25T01:30:00+5:30\",\"endDateTime\":\"2017-02-25T02:00:00+5:30\"}";
				

				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.write(urlParameters.getBytes());
				wr.flush();
				wr.close();

				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				//print result
				System.out.println(response.toString());

			}*/
		
		// HTTP POST request
		private String sendPost() throws Exception {

			OkHttpClient client = new OkHttpClient();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String startDate = simpleDateFormat.format(new Date())+"+05:30";
			String endDate = simpleDateFormat.format(new Date(new Date().getTime()+300000))+"+05:30";

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, "{\n\"startDateTime\":\""+startDate+"\",\n\"endDateTime\":\""+endDate+"\"\n}");
			Request request = new Request.Builder()
			  .url("http://10.222.120.163:3001/getAllRoomsStatus")//10.222.120.163:3001/getAllRoomsStatus")
			  .post(body)
			  .addHeader("content-type", "application/json")
			  .addHeader("cache-control", "no-cache")
			  .addHeader("postman-token", "2738f157-b458-ade3-216e-dabd91664a56")
			  .build();

			Response response = client.newCall(request).execute();


			//print result
			System.out.println(response.toString());
			ResponseBody responseBody = response.body();
			String responseJson = responseBody.source().readUtf8();
			
			return responseJson;
		}


			
	}


