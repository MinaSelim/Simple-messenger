package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer
{
	private Socket sock;
	private Clients clients = new Clients();
	public static void main(String[] args)
	{
		ChatServer server = new ChatServer();
		server.startServer();
	}
	

	public void startServer()
	{
		try
		{
		
		@SuppressWarnings("resource")
		ServerSocket serversock = new ServerSocket(9999);
		
		
		while(true)
		{		
				sock = serversock.accept();
				clients.addClient(sock);
				Runnable receive = new DataReceiver(sock);
				Thread sockets = new Thread(receive);
				sockets.start();
				System.out.println("added a socket");			
		}
		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class DataReceiver implements Runnable
	{
		private Socket socket;
		private DataReceiver(Socket socket) 
		{
			this.socket = socket;
			
		}

		public void run() 
		{
			read();
		}
		
		private void read() 
		{
			String message;
			try 
			{
			BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while(true)
			{
			
				if((message = read.readLine()) != null) 
				{
					clients.setMessage(message);
					clients.sendMessageToAll();
				}
			}
			}
			catch(Exception e) 
			{
				clients.removeClient(socket);
			}
		}
	}




}
