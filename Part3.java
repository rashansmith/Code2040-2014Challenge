import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class thePrefix 
{
	public static void main(String[] args) throws Exception
	{
	    //requesting the prefix and array
	    String reqStr = "{\"token\" : \"UtB497SiS6\"}";
            URL url = new URL( "http://challenge.code2040.org/api/prefix" );  
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
	    
	    //get results that contain prefix and array
	    Scanner scan = new Scanner(rc.getInputStream());
	    String input = scan.nextLine();
	    //System.out.println(input);
	   
	    //various dissections done to seperate Prefix and Array in the result sent 
	    String[] parts = input.split(":");
	    String pre = parts[3];
	    String[] partss = pre.split("}");
	    String pree = partss[0];
	    String Prefix = pree.replace("\"", "");
	    String Array1 = parts[2];
	    String[] partt = Array1.split("prefix");
	    String Array = partt[0];
	    String arrAY =  Array.replace("[", "");
	    String TheArray = arrAY.replace("]", "");
	    String aRRAY = TheArray.replace(",", " ");
	    String ARRAY = aRRAY.replace("\"", "");
	    String[] finall = ARRAY.split(" ");
	    
	    //CertainPrefix method in action, searching and pulling out words in an array that do NOT contain the prefix
	    ArrayList<String> newList = certainPrefix(Prefix, finall);
	    //other mini technical tweaks to the result to convert it to a format that can be submitted
	    String w = "";
	    for(int i = 0; i <newList.size(); i++)
	    {
	    	if(i == newList.size()-1)
	    	{
	    		 w += '"' + newList.get(i) + '"';
	 	}
	    	else
    		{
    			 w+= '"' + newList.get(i) + '"' + ',';
    		}
	    }
	    String answer = "[" + w + "]";
	    
	    //Print statements so I can see whats going on
	    System.out.println("The array elements are : " + ARRAY);
	    System.out.println("The prefix is : " + Prefix);
	    System.out.println("The array of strings that do not contain " + Prefix + " is " + answer);
	    scan.close();
	    
	    
	    //sending the Prefix-less Array back to the server
	    String prfx = "{\"token\" : \"UtB497SiS6\", \"array\" :" + answer + " }";
	    URL url2 = new URL( "http://challenge.code2040.org/api/validateprefix" );  
	    HttpURLConnection rca = (HttpURLConnection)url2.openConnection();  
	    rca.setRequestMethod("POST");  
	    rca.setDoOutput( true );  
	    rca.setDoInput( true );   
	    rca.setRequestProperty( "Content-Type", "application/json; charset=utf-8" );
            int lens = prfx.length();  
	    rca.setRequestProperty( "Content-Length", Integer.toString( lens ) );  
	    rca.connect(); 
	    OutputStreamWriter outt = new OutputStreamWriter(rca.getOutputStream());   
	    outt.write( prfx, 0, lens );  
	    outt.flush();
	    
	    //get response from endpoint
	    Scanner scann = new Scanner(rca.getInputStream());
	    while(scann.hasNextLine()) System.out.println(scann.nextLine());
	    scann.close();
	}
	
	    //Method to find strings without certain prefix
	    public static ArrayList<String> certainPrefix(String word, String[] words)
		{
			Integer i;
			ArrayList<String> newwords = new ArrayList();
			for( i = 0; i < words.length; i++)
			{
				if(words[i].contains(word) != true)
				{
					newwords.add(words[i]);
				}
			}
			return newwords;
		}
}
