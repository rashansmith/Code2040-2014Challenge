//This program receives the random string to be reversed, and reverses it!

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



public class reverse {
	
		// Requesting the string to be reversed
		public static void main(String[] args) throws Exception
		{
		    String myId = "{\"token\" : \"UtB497SiS6\"}";
		    URL url = new URL( "http://challenge.code2040.org/api/getstring" );  
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
		    
		    //get token to be sent back
		    Scanner scan = new Scanner(rc.getInputStream());
		    
		    //dissecting the sent result to get the reversed string alone
		    String newString = "";
		    while(scan.hasNextLine())
		    {
		    String input = scan.nextLine();
		    System.out.println(input);
		    String[] parts = input.split(":");
		    String output = parts[1];
		    for (int i = 0; i < output.length(); i++)
		    {
		    	char c = output.charAt(i);
		        if (Character.isLetter(c))
		        {
		    		newString += c;
		        }
		    }
		    System.out.println("The reversed string is " + newString);
		    }
		    
		    //Reversing the string sent
		    String result = reversethestring(newString);
		    scan.close();
		    
		    
		    
		    //submitting the Reversed String 
		    String reversedString = "{\"token\" : \"UtB497SiS6\", \"string\" :" + "\"" + result + "\"" + " }";
		    URL url2 = new URL( "http://challenge.code2040.org/api/validatestring" );  
		    HttpURLConnection rca = (HttpURLConnection)url2.openConnection();  
		    rca.setRequestMethod("POST");  
		    rca.setDoOutput( true );  
		    rca.setDoInput( true );   
		    rca.setRequestProperty( "Content-Type", "application/json; charset=utf-8" );
		    int lens = reversedString.length();  
		    rca.setRequestProperty( "Content-Length", Integer.toString( lens ) );  
		    rca.connect(); 
		    OutputStreamWriter outt = new OutputStreamWriter(rca.getOutputStream());   
		    outt.write( reversedString, 0, lens );  
		    outt.flush();
		    
		    //get string which informs me whether I have passed the challenge or not
		    Scanner scann = new Scanner(rca.getInputStream());
		    while(scann.hasNextLine()) System.out.println(scann.nextLine());
		    scann.close();
		}
		    
		    
		//the method that reverses the string
		public static String reversethestring(String n)
			{
				String original = n;
				String reversedString = "";
				
				if(original.length() == 1)
				{
		            		return n;
				} 
				else 
				{
		            		reversedString += original.charAt(original.length()-1) + reversethestring(original.substring(0,original.length()-1));
		            		return reversedString;
		        	}
			}	
	}




