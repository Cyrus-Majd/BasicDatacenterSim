package com.cyrus.har.AttackCodes;

	import javax.ws.rs.Consumes;
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.Produces;
	import javax.ws.rs.QueryParam;
	import javax.ws.rs.client.Client;
	import javax.ws.rs.client.ClientBuilder;
	import javax.ws.rs.core.MediaType;

	import org.json.JSONException;
	import org.json.JSONObject;

	@Path("/attackedserver/{attackCount}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public class AttackedServer {

	 int returnValue;
	 int randomNumber;
	 String storageRandomNumber;
	 JSONObject jsonObject = new JSONObject();
	 final Client client = ClientBuilder.newClient();

	 // ====================Method called for attacking the Storage====================
	 @Path("/")
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getJson(
	      @PathParam("attackCount") int attackCount,
	      @QueryParam("rngCount") int rngCount) throws JSONException, InterruptedException {
	  
	  Long serverTime_in = System.currentTimeMillis();

	  // ---------------------Client Call to Storage---------------------------
	  // http://localhost:8082/attackcode/webapi/storage/2 -> Sync. Storage
	  String response = client.target("http://10.0.0.37:8080/AttackCodes/webapi/attackedStorage/" + attackCount + "/?rngCount=" + rngCount)
	    .request(MediaType.TEXT_PLAIN).get(String.class);

	  System.out.println("Attacked Server Json: " + response);
	  storageRandomNumber = response.substring(139, 141).trim(); // substring(43, 45), 140
	  if (storageRandomNumber.contains("}")) {storageRandomNumber = storageRandomNumber.substring(0, 1).trim();}

	  returnValue = Integer.valueOf(storageRandomNumber);
	  // ------------------------End of REST Client----------------------------

	  Long serverTime_out = System.currentTimeMillis();
	  jsonObject.put("attackCountValue", attackCount).put("serverReturnValue", returnValue);

	  System.out.println("Attacked Server Call Delay: " + (serverTime_out - serverTime_in));

	  return jsonObject.toString();
	 }

	}
