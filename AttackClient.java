package com.cyrus.har.AttackCodes;

	import java.util.Scanner;
	import java.util.concurrent.Executors;
	import java.util.concurrent.ScheduledExecutorService;
	import java.util.concurrent.TimeUnit;

	import javax.ws.rs.client.Client;
	import javax.ws.rs.client.ClientBuilder;
	import javax.ws.rs.core.MediaType;

	import org.json.JSONException;

	public class AttackClient {

	 public static void main(String[] args) throws JSONException {

	  final Client client = ClientBuilder.newClient(); 
	  int attackPeriod;
	  final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	  Scanner scanner = new Scanner(System.in);
	  System.out.println("Type in the attack period in miliseconds: \n");
	  attackPeriod = scanner.nextInt();
	  scanner.close();

	  executorService.scheduleAtFixedRate(new Runnable() {

	   @Override
	   public void run() {

	    // --------------------Begin of Attack Calls------------------------
	    // localhost:8082/attackcode/webapi/server/1 -> Synchronous Server
	    // localhost:8082/attackcode/webapi/storage/1 -> Synchronous Storage
	    String response = client.target("http://10.0.0.48:8080/AttackCodes/webapi/attackedserver/2/?rngCount=25000000") // at 750,000 the query time is ~25ms; at 75,000 the query time is ~2.5ms; 7,500,000 ~ 200ms; 15,000,000 ~ 380ms
	      .request(MediaType.TEXT_PLAIN).get(String.class);

	    System.out.println("Respnse to client: " + response);
	   }
	  }, 0, attackPeriod, TimeUnit.MILLISECONDS);

	 }

	}
