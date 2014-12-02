//this program finds the needle in the Haystack :)

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



public class needleInHayStack 
{
	public static void main(String[] args) throws Exception
	{
	    //Submitting the request to receive my needle and Haystack
            String reqStr = "{\"token\" : \"UtB497SiS6\"}";
            URL url = new URL( "http://challenge.code2040.org/api/haystack" );  
	    HttpURLConnection rc = (HttpURLConnection)url.openConnection();  
	    rc.setRequestMethod("POST");  
	    rc.setDoOutput( true );  
	    rc.setDoInput( true );   
	    rc.setRequestProperty( "Content-Type", "application/json; charset=utf-8" );
            int len = reqStr.length();  
	    rc.setRequestProperty( "Content-Length", Integer.toString( len ) );  
	    rc.connect(); 
	    OutputStreamWriter out = new OutputStreamWriter( rc.getOutputStream() );   
	    out.write( reqStr, 0, len );  
	    out.flush();
	    
	    //get tokens(needle and Hay) 
	    Scanner scan = new Scanner(rc.getInputStream());
	    String input = scan.nextLine();
	    System.out.println(input);
	   
	    //seperating the Needle and Haystack from the string it was sent in
	    String[] parts = input.split(":");
	    String needle1 = parts[3];
	    String[] partss = needle1.split("}");
	    String Needlee = partss[0];
	    String Needle = Needlee.replace("\"", "");
	    String Haystack1 = parts[2];
	    String[] partt = Haystack1.split("needle");
	    String Haystack = partt[0];
	    String haystck =  Haystack.replace("[", "");
	    String TheHaystack = haystck.replace("]", "");
	    String haySTACK = TheHaystack.replace(",", " ");
	    String HAYSTACK = haySTACK.replace("\"", "");
	    String[] finall = HAYSTACK.split(" ");
	    
	    //calculations to find index of Needle in Haystack
	    Integer answer = findNeedle(Needle, finall);
	    
	    //printing results at different stages so I can see whats going on
	    System.out.println("The haystack is : " + HAYSTACK);
	    System.out.println("The needle is : " + Needle);
	    System.out.println("The index of th needle in the haystack is : " + answer);

	    scan.close();
	    
	    
	    //submitting the index of the needle 
	    String needleInHay = "{\"token\" : \"UtB497SiS6\", \"needle\" :" + answer + " }";
            URL url2 = new URL( "http://challenge.code2040.org/api/validateneedle" );  
	    HttpURLConnection rca = (HttpURLConnection)url2.openConnection();  
	    rca.setRequestMethod("POST");  
	    rca.setDoOutput( true );  
	    rca.setDoInput( true );   
	    rca.setRequestProperty( "Content-Type", "application/json; charset=utf-8" );
	    int lens = needleInHay.length();  
	    rca.setRequestProperty( "Content-Length", Integer.toString( lens ) );  
	    rca.connect(); 
	    OutputStreamWriter outt = new OutputStreamWriter(rca.getOutputStream());   
	    outt.write( needleInHay, 0, lens );  
	    outt.flush();
	    
	    //find out whether I passed the challenge or not
	    Scanner scann = new Scanner(rca.getInputStream());
	    while(scann.hasNextLine()) System.out.println(scann.nextLine());
	    scann.close();
	}
	   
	/*method that finds the needle in Haystack. The method loops through the haystack array with a for loop
	and using an if statement searches for a string in they haystack that also matches the Needle. As a result
	it returns the index of that string in the array(haystack)*/
	public static Integer findNeedle(String Needlle, String[] Haystackk)
	{
		Integer found;
		for(found = 0; found < Haystackk.length; found++)
		{
			if(Haystackk[found].equals(Needlle))
			{
				 return found; 
			}
		}
		return found;
	}
	

}
