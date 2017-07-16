package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Clients 
{
	private List<Socket> sockets = new ArrayList<Socket>();
	private String message;
	
	public void addClient(Socket client) 
	{
		sockets.add(client);
		System.out.println(sockets);
	
	}
	
	public List<Socket> getClients()
	{
		List<Socket> ListClone = new ArrayList<Socket>();
		ListClone.addAll(sockets);
		return ListClone;
		
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	public void sendMessageToAll() 
	{
		for(int i = 0; i<sockets.size(); i++)
		{
			try 
			{
			if(sockets.get(i).isClosed()) 
			{
				sockets.remove(i);
				System.out.println("socket removed");
			}
				
			else 
			{
				PrintWriter writer = new PrintWriter(sockets.get(i).getOutputStream());
				writer.println(message);
				writer.flush();
			}
			
			}
			catch(IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
}
