package client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Utility 
{
	private static Formatter test1;
	public static void CreateFile(String a)
	{
		try
		{
			test1 = new Formatter(a);
			System.out.println("you created a "+ a+" file");
		}
		catch(Exception e)
		{
			System.out.println("error, file not created");
		}
	}
	public static void writeToFile(String a)
	{
		test1.format("%s%n", a);
		
	}
	public static void closeFile()
	{
		test1.close();
	}
	public static ArrayList<String> Read(String name) throws IOException
	{
	
		ArrayList<String> data = new ArrayList<String>();
		File file = new File(name);
		if(file.exists()) 
		{
			Scanner read = new Scanner(file);

			while(read.hasNextLine())
			{
				data.add(read.nextLine());
			}
			read.close();
			return data;
		}
		else
			return null;	
	}
	
	public static boolean  fileExists(String name) 
	
	{
		File file = new File(name);
		return file.exists();
	}
	
	public static String getValue(String line) throws Exception 
	{
		Exception e = new Exception();
		for(int i=0 ; i<line.length(); i++) 
		{
			if(line.charAt(i)==' ') 
			{
				String value = line.substring(i+1);
				return value;
			}
		}
		throw e ;
	}
	
	public static boolean isInteger( String input ) 
	{
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( NumberFormatException e ) {
	        return false;
	    }
	}
		

	
}
