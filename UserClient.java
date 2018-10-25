package com.cyrus.har.AttackCodes;

	import java.util.ArrayList;
	import java.util.Scanner;
	import java.util.concurrent.TimeUnit;

	//import javax.sound.midi.Soundbank;
	import javax.ws.rs.client.Client;
	import javax.ws.rs.client.ClientBuilder;
	import javax.ws.rs.core.MediaType;

	import org.json.JSONException;

	public class UserClient {

	 public static void main(String[] args) throws JSONException, InterruptedException {

	  final Client client = ClientBuilder.newClient();
	  int hitPeriod;
	  int numberOfCalls;
	  int round;
	  int theoreticalCalllength;
	  int rngCount;
	  double averageCallInterval;
	  Long callInterval;
	  Long clientTime_init;
	  Long clientTime_in;
	  Long clientTime_out;
	  
	  Long serverTime_in;
	  Long serverTime_out;
	  
	  Long storageTime_in;
	  Long storageTime_out;
	  
	  String serverResponse_in;
	  String serverResponse_out;
	  String storageResponse_in;
	  String storageResponse_out;
	  
	  ArrayList<ArrayList<Long>> clientTimeList_in = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> clientTimeList_out = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> clientTimeList_delta = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> clientTimeList_total = new ArrayList<ArrayList<Long>>();

	  ArrayList<ArrayList<Long>> serverTimeList_in = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> serverTimeList_out = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> serverTimeList_delta = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> serverTimeList_total = new ArrayList<ArrayList<Long>>();

	  ArrayList<ArrayList<Long>> storageTimeList_in = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> storageTimeList_out = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> storageTimeList_delta = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Long>> storageTimeList_total = new ArrayList<ArrayList<Long>>();

	  ArrayList<ArrayList<Long>> callIntervalList = new ArrayList<ArrayList<Long>>();
	  ArrayList<ArrayList<Double>> averageCallIntervalList = new ArrayList<ArrayList<Double>>();
	  
	  ArrayList<Long> finalCalllengthList = new ArrayList<Long>();
	  ArrayList<Integer> theoreticalCalllengthList = new ArrayList<Integer>();
	  ArrayList<Double> actualCalllengthList = new ArrayList<Double>();
	  ArrayList<Double> calllengthRatioList = new ArrayList<Double>();
	  ArrayList<Double> actualCalllengthRatioList = new ArrayList<Double>();
	  
	  
	  
	  Scanner scanner = new Scanner(System.in);
	   System.out.println("Send calls in every \"X\" miliseconds: X=");
	   hitPeriod = scanner.nextInt();
	   System.out.println("Number of calls: ");
	   numberOfCalls = scanner.nextInt();
	   System.out.println("Number of rounds of measurements: ");
	   round = scanner.nextInt();
	   rngCount = 11000000;
	  scanner.close();

	  clientTime_init = System.currentTimeMillis();

	  // -----------Begin of User Call-----------
	  for(int i=0; i<round; i++){
	   clientTimeList_in.add(new ArrayList<Long>());
	   clientTimeList_out.add(new ArrayList<Long>());
	   clientTimeList_delta.add(new ArrayList<Long>());
	   clientTimeList_total.add(new ArrayList<Long>());
	   
	   serverTimeList_in.add(new ArrayList<Long>());
	   serverTimeList_out.add(new ArrayList<Long>());
	   serverTimeList_delta.add(new ArrayList<Long>());
	   serverTimeList_total.add(new ArrayList<Long>());
	   
	   storageTimeList_in.add(new ArrayList<Long>());
	   storageTimeList_out.add(new ArrayList<Long>());
	   storageTimeList_delta.add(new ArrayList<Long>());
	   storageTimeList_total.add(new ArrayList<Long>());
	   
	   for(int j=0; j<numberOfCalls; j++){
	    TimeUnit.MILLISECONDS.sleep(hitPeriod);
	    clientTime_in = System.currentTimeMillis();
	  
	     // localhost:8082/attackcode/webapi/storage/1 -> Sync. Storage
	     String response = client.target("http://10.0.0.47:8080/AttackCodes/webapi/server/1/?rngCount=" + rngCount) // at 750,000 the query time is ~25ms; at 75,000 the query time is ~2.5ms; 7,500,000 ~ 200ms; 15,000,000 ~ 380ms
	      .request(MediaType.TEXT_PLAIN).get(String.class);
	      
	      serverResponse_in = response.substring(58, 71).trim();  // parse String JSON and assign to local variables
	      serverResponse_out = response.substring(181, 194).trim();
	      storageResponse_in = response.substring(99, 112).trim();
	      storageResponse_out = response.substring(140, 153).trim();
	    
	      serverTime_in = Long.valueOf(serverResponse_in);
	      serverTime_out = Long.valueOf(serverResponse_out);
	      storageTime_in = Long.valueOf(storageResponse_in);
	      storageTime_out = Long.valueOf(storageResponse_out);
	    
//	      System.out.println("Client Json: " + response);
	    clientTime_out = System.currentTimeMillis();
	    
	    // ...........End Time of the Loop...........
	    
	    
	 
	    //========= Client ============== 
	    clientTimeList_in.get(i).add(clientTime_in);    
	    clientTimeList_out.get(i).add(clientTime_out);
	    clientTimeList_delta.get(i).add(clientTime_out - clientTime_in);
	    clientTimeList_total.get(i).add(clientTime_out - clientTimeList_in.get(i).get(0));
	    
	    //========= Server ==============
	    serverTimeList_in.get(i).add(serverTime_in);
	    serverTimeList_out.get(i).add(serverTime_out);
	    serverTimeList_delta.get(i).add(serverTime_out - serverTime_in);
	    serverTimeList_total.get(i).add(serverTime_out - serverTimeList_in.get(i).get(0)); 
	    
	    //========= Storage ==============
	    storageTimeList_in.get(i).add(storageTime_in);
	    storageTimeList_out.get(i).add(storageTime_in);
	    storageTimeList_delta.get(i).add(storageTime_out - storageTime_in);
	    storageTimeList_total.get(i).add(storageTime_out - storageTimeList_in.get(i).get(0));
	    // ----------------End of User Call--------------


	     }

	  }

	  
	  // calculate the list of call intervals
	  for(int j=0; j<round; j++){
	   callIntervalList.add(new ArrayList<Long>());
	   for(int i=0; i<(numberOfCalls-1); i++){
	    callInterval = clientTimeList_in.get(j).get(i+1) - clientTimeList_in.get(j).get(i);
	    callIntervalList.get(j).add(callInterval);
	   }
	  }
	  
	  // calculate the "average call interval" and the "actual call lengths" at the client side for each round

	  averageCallInterval = 0.0;
	  for(int j=0; j<round; j++){
	   Long sum = 0L;
	   averageCallIntervalList.add(new ArrayList<Double>());
	   for(int i=1; i<(numberOfCalls-1); i++){
	    sum = sum + callIntervalList.get(j).get(i);
	    averageCallInterval = (Double.valueOf(sum) / (numberOfCalls-2.0));
	   }
	   averageCallIntervalList.get(j).add(averageCallInterval);
	   actualCalllengthList.add(averageCallInterval * numberOfCalls);
	  }
	  
	  System.out.println("Client Timestamp in: " + clientTimeList_in);
	  System.out.println("Client Timestamp Out: " + clientTimeList_out);
	  System.out.println("Client Call Interval: " + callIntervalList);
	  System.out.println("Client Call interval - Average: " + averageCallIntervalList);
	  System.out.println("Clientside Call Delay: " + clientTimeList_delta);
	  System.out.println("Clientside cumulative length of the call: " + clientTimeList_total);
	  System.out.println();
	  System.out.println("Server Timestamp In: ," + serverTimeList_in);
	  System.out.println("Server Timestamp Out: ," + serverTimeList_out);
	  System.out.println("Serverside Call Delay: ," + serverTimeList_delta);
	  System.out.println("Serverside cumulative length of the call: ," + serverTimeList_total);
	  System.out.println();
	  System.out.println("Storage Timestamp In: ," + storageTimeList_in);
	  System.out.println("Storage Timestamp Out: ," + storageTimeList_out);
	  System.out.println("Storageside length of the call : ," + storageTimeList_delta);
	  System.out.println("Storageside cumulative length of the call: ," + storageTimeList_total);
	  
	  // calculating the "final length of calls", the "theoretical length of calls", and the "ratio of actual to theoretical length of calls"
	  for(int i=0; i<round; i++){
	   finalCalllengthList.add(clientTimeList_total.get(i).get(numberOfCalls-1));
	   theoreticalCalllength = hitPeriod * numberOfCalls;
	   theoreticalCalllengthList.add(theoreticalCalllength);
	   calllengthRatioList.add(Double.valueOf(finalCalllengthList.get(i)) / Double.valueOf(theoreticalCalllengthList.get(i)));
	   actualCalllengthRatioList.add(Double.valueOf(finalCalllengthList.get(i)) / Double.valueOf(actualCalllengthList.get(i)));
	   
	  }
	  
	  System.out.println();
	  System.out.println("Theoretical Length of calls: " + theoreticalCalllengthList);
	  System.out.println("Measured Length of calls: " + finalCalllengthList);
	  System.out.println("Ratio of \"Measured Length\" to \"Theoreticla Length\": " + calllengthRatioList);
	  System.out.println();
	  System.out.println("Actual Calculated Length of calls: " + actualCalllengthList);  // Added New
	  System.out.println("Ratio of \"Measured Length\" to \"Actual Length\": " + actualCalllengthRatioList);
	  
	  
	  Long clientTime_final = System.currentTimeMillis();
	  
	  // ................Start Time of the Web Call ..........................
	  System.out.println();
	  System.out.println("Call Period: " + hitPeriod);
	  System.out.println("Number of calls in each test round: " + numberOfCalls);
	  System.out.println("Number of test rounds: " + round);
	  System.out.println("The RNG Count: " + rngCount);
	  
	  System.out.println();
	  System.out.println("Timestamp at the begin of the run: " + clientTime_init);
	  System.out.println("Timestamp at the end of the run: " + clientTime_final);
	  System.out.println("Duration of the run: " + (clientTime_final - clientTime_init));
	  System.out.println("Each \"run\" consists of " + round + " rounds.");
	  // .....................................................................
	 }

	}
