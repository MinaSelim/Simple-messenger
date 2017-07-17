package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ChatClientGUI extends JFrame 
{

	private JTextField send_message;
	private final String username;
	private JTextArea chat; 
	private Socket socket;
	private BufferedReader read;
	private PrintWriter writer;
	private ChatClientOutput output;

	public ChatClientGUI(String IP_address , int port, String username)
	{
		super("Chat Client");
		this.username = username;
		try
		{
			socket = new Socket( IP_address , port);
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Cannot connect to server");
			e.printStackTrace();
			System.exit(-1);
		}
		super.addWindowListener( new WindowListener() 
		{

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				try 
				{
					socket.close();
				}
				catch(IOException e) 
				{
					e.printStackTrace();
				}
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
				
				
				);
		send_message = new JTextField("Please enter your message here       ");
		chat = new JTextArea();
		chat.setLineWrap(true);
		chat.setWrapStyleWord(true);
		chat.setEditable(false);
		output = new ChatClientOutput();
		JScrollPane scroll = new JScrollPane(chat);
		DefaultCaret caret = (DefaultCaret)chat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(BorderLayout.CENTER, scroll);
		TheListener listener = new TheListener();
		add(BorderLayout.SOUTH, send_message);
		
		send_message.addActionListener(listener);
		Runnable read = new ChatClientInput();
		Thread reader = new Thread(read);
		reader.start();
		
	}
	
	private class TheListener implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) 
		{	
			if(send_message.getText().length()>0) 
			{
				output.sendMessage(send_message.getText());
				send_message.setText(null);	
				send_message.requestFocus();
			}
		}
		
	}
	
	private class ChatClientOutput
	{
		public void sendMessage(String message)
		{
			try
			{
				writer.println(username + ": " + message);
				writer.flush();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public class ChatClientInput implements Runnable
	{
		public void run()
		{
			readMessages();
			System.out.println("readMessages done");
		}
		
		public void readMessages()
		
		{
			
			try
			{
				while(true)
				{
					System.out.println("reading");
					String message;
					if((message = read.readLine()) != null)
					{
						chat.append(message + '\n');
					}
					else
						System.out.println("no messages");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	
	
	
	

}
