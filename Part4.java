//datestamp challenge

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.TimeZone;


public class dateStampp
{
	public static void main (String[] args) throws Exception
	{  
	    //posting my personal token to receive the datestamp and interval needed to complete the challenge	
	    String myId = "{\"token\" : \"UtB497SiS6\"}";
	    URL url = new URL( "http://challenge.code2040.org/api/time" );  
	    HttpURLConnection rc = (HttpURLConnection)url.openConnection();  
	    rc.setRequestMethod("POST");  
	    rc.setDoOutput( true );  
	    rc.setDoInput( true );   
	    rc.setRequestProperty( "Content-Type", "application/json; charset=utf-8" );
	    int len = myId.length();  
	    rc.setRequestProperty( "Content-Length", Integer.toString( len ) );  
	    rc.connect(); 
	    OutputStreamWriter out = new OutputStreamWriter( rc.getOutputStream() );   
	    out.write( myId, 0, len );  
	    out.flush();
	    
	    //printing out interval and datestamp
	    Scanner scan = new Scanner(rc.getInputStream());
	    String inputt = "";
	    while(scan.hasNextLine())
	    {
	    	 inputt = scan.nextLine();
	    }
	    //seperating the interval and datestamp from their current string format
	    String[] inputtt = inputt.split(":");
	    String input2 = inputtt[2] + ":" + inputtt[3] + ":" + inputtt[4];
	    String[] input3 = input2.split(",");
	    String input4 = input3[0];
	    String inputFinall = input4.replaceAll("\\.", ":");
	    String inputFinalll =  inputFinall.replaceAll("\"", "") ;
	    String interval = inputtt[5];
	    String intervalFinal = interval.replaceAll("}", "");
	    Integer intervalFinall = Integer.parseInt(intervalFinal);
	    char[] testin = inputFinalll.toCharArray();
	    testin[19] = '.';
	    String theresult = String.valueOf(testin);
	    
	    //println statements to keep track/make sure the interval and datestamp are seperated correctly
	    System.out.println(inputt);
	    System.out.println("Interval: " + intervalFinall);
	    System.out.println("DateStamp: " + theresult);
	    
	    //method to calculate new datestamp with added interval
	    String theanswer = datestampUpdate(theresult, intervalFinall);
	    System.out.println("The result is : " + theanswer);
	   
	    scan.close();
	    
	    
	    //sending new datestamp to be graded
	    String newStamp = "{\"token\" : \"UtB497SiS6\", \"datestamp\" : " +  '"' + theanswer +  '"' + " }";
	    System.out.println(newStamp);
	    URL url2 = new URL( "http://challenge.code2040.org/api/validatetime" );  
	    HttpURLConnection rca = (HttpURLConnection)url2.openConnection();  
	    rca.setRequestMethod("POST");  
	    rca.setDoOutput( true );  
	    rca.setDoInput( true );   
	    rca.setRequestProperty( "Content-Type", "application/json; charset=utf-8" );
	    int lens = newStamp.length();  
	    rca.setRequestProperty( "Content-Length", Integer.toString( lens ) );  
	    rca.connect(); 
	    
	    //send info.
	    OutputStreamWriter outt = new OutputStreamWriter(rca.getOutputStream());   
	    outt.write( newStamp, 0, lens );  
	    outt.flush();
	    
	    //get results of whether I passed the challenge or not
	    Scanner scann = new Scanner(rca.getInputStream());
	    while(scann.hasNextLine()) System.out.println(scann.nextLine());
	    scann.close();
	}
	
	/*The method that adds the interval to the datestamp, and returns the new datestamp. In doing this I imported the java 
	packages for Gregorian Calendar and Date mainly.In the method the datestamp is converted to a Gregorian Calender
	date, which allows for the seconds to be easily added. Then it is converted back to ISO 8601 time format. The method
	takes in a string (which is the original datestamp) and also an integer (which is the seconds to be added to the datestamp.*/
	public static String datestampUpdate(String r, Integer seconds) throws ParseException 
	{
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String oldDate = r;
		Date d = formatter.parse(oldDate);
		GregorianCalendar hi = new GregorianCalendar();
		hi.setTime(d);
		hi.add(hi.SECOND, seconds);
		Date end = hi.getTime();
		SimpleDateFormat dfg = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" );
		String prg =  dfg.format(end);
		System.out.println("The old date : " + oldDate);
		System.out.println("The new date : " + prg );	
		System.out.println(prg);
		return prg;
	}

}


