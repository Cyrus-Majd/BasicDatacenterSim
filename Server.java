package com.cyrus.har.AttackCodes;

	import java.util.ArrayList;

	import javax.ws.rs.Consumes;
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.Produces;
	import javax.ws.rs.QueryParam;
	import javax.ws.rs.client.Client;
	import javax.ws.rs.client.ClientBuilder;
	import javax.ws.rs.core.MediaType;

	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;

	@Path("/server/{attackCount}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public class Server {

	 int returnValue;
	 int randomNumber;
	 JSONObject jsonObject = new JSONObject();
	// JSONArray jsonArray = new JSONArray();
	 final Client client = ClientBuilder.newClient(); 
	 
	 // ====================Method called for attacking the Storage================
	 @Path("/")
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getJson(
	      @PathParam("attackCount") int attackCount,
	      @QueryParam("rngCount") int rngCount) throws JSONException, InterruptedException {
	  
	  
	  String storageResponse_in;
	  String storageResponse_out;
	  String storageRandomNumber;
	  Long storageTime_in;
	  Long storageTime_out;
	  Long serverTime_in = System.currentTimeMillis();

	  // -----------------Client Call to Storage-----------------------
	  // http://localhost:8082/attackcode/webapi/storage/1 -> Sync. Storage
	  String response = client.target("http://10.0.0.37:8080/AttackCodes/webapi/storage/" + attackCount + "/?rngCount=" + rngCount)
	    .request(MediaType.TEXT_PLAIN).get(String.class);

	  
	  System.out.println();
	  System.out.println("Server Json: " + response);
	  
	  storageResponse_in = response.substring(28, 41).trim(); 
	  storageResponse_out = response.substring(69, 82).trim();  
	  storageRandomNumber = response.substring(139, 141).trim(); 
	  if (storageRandomNumber.contains("}")) {storageRandomNumber = storageRandomNumber.substring(0, 1).trim();}

	  storageTime_in = Long.valueOf(storageResponse_in);  
	  storageTime_out = Long.valueOf(storageResponse_out);
	  returnValue = Integer.valueOf(storageRandomNumber);
	  // ----------------End of REST Client-----------------------

	  
	  Long serverTime_out = System.currentTimeMillis();
	  
	  jsonObject.put("storage_StartTimeStamp__", storageTime_in)
	      .put("storage___EndTimeStamp__", storageTime_out)
	      .put("server__StartTimeStamp__", serverTime_in)
	      .put("server____EndTimeStamp__", serverTime_out)
	       .put("attack______CountValue__", attackCount)
	      .put("server_____ReturnValue__", returnValue);
	//  jsonArray.put(jsonObject);
	  
	  return jsonObject.toString();
	 }

	}
