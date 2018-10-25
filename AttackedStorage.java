package com.cyrus.har.AttackCodes;

	import javax.ws.rs.Consumes;
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.Produces;
	import javax.ws.rs.QueryParam;
	import javax.ws.rs.core.MediaType;

	import org.json.JSONException;
	import org.json.JSONObject;

	@Path("/attackedStorage/{attackCount}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public class AttackedStorage {

	 int returnValue;
	 int randomNumber;
	 JSONObject jsonObject = new JSONObject();
	// JSONArray jsonArray = new JSONArray();

	 // ======================Method called for attacking the Storage====================
	 @Path("/")
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getJson(
	      @PathParam("attackCount") int attackCount,
	      @QueryParam("rngCount") int rngCount) throws JSONException, InterruptedException {
	  
	  Long storageTime_in = System.currentTimeMillis();

	  // ----------------------Querying Storage---------------------------------
	  for (int i = 0; i <= rngCount; i++) {  // at 750,000 the query time is ~25ms; at 75,000 the query time is ~2.5ms;  7,500,000 ~ 200ms; 15,000,000 ~ 380ms
	   randomNumber = (int) (Math.random() * 50 + 11);
	  }
	  // -----------------------End of Query-----------------------------

	  Long storageTime_out = System.currentTimeMillis();  
	  
	  jsonObject.put("attack______CountValue__", attackCount)
	     .put("storage____ReturnValue__", randomNumber)
	     .put("storage_StartTimeStamp__", storageTime_in)
	     .put("storage___EndTimeStamp__", storageTime_out);  
	//  jsonArray.put(jsonObject);

	  
	  System.out.println();
	  System.out.println("Storage Json: " + jsonObject);

	  return jsonObject.toString();
	 }
	 // =========================================================================

	}
