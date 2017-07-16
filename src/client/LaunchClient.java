package client;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LaunchClient 
{
	public static void main(String[] args)
	{
		int  port=0;
		String ip_address=null, username=null; 
		ArrayList<String> data = new ArrayList<String>();
		if(Utility.fileExists("config.txt")) 
		{
			try 
			{
				data = Utility.Read("config.txt");
				ip_address = Utility.getValue(data.get(0));
				username = Utility.getValue(data.get(1));
				String port_string = Utility.getValue(data.get(2));
				port = Integer.parseInt(port_string);
			}
			
			catch(Exception e) 
			{
				JOptionPane.showMessageDialog(null, "data is corrupted, please delete your config file");
				System.exit(-1);
				e.printStackTrace();
			}
			
		}
		else 
		{
			ip_address = JOptionPane.showInputDialog("Please enter the IP_Address");
			String port_String = JOptionPane.showInputDialog("Please enter port number");
			while(!Utility.isInteger(port_String)) 
			{
				port_String = JOptionPane.showInputDialog("Please enter a number");
			}
			port = Integer.parseInt(port_String);
			username = JOptionPane.showInputDialog("Please enter your username");
			Utility.CreateFile("config.txt");
			Utility.writeToFile("Ip_Address: " + ip_address);
			Utility.writeToFile("Username: " + username);
			Utility.writeToFile("Port: " + port);
			Utility.closeFile();
			
		}
		
		
		ChatClientGUI client =new ChatClientGUI( ip_address, port, username);
		client.setSize(500,500);
		client.setVisible(true);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
