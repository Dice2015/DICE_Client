package de.uks.se.scoreproject.dice.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;

import de.uks.se.scoreproject.dice.startup.StartupInitializer;


public class NetworkClient extends Thread  {

	private String ip;
	private int port;
	private String username;
	private String pw;
	private boolean connected = false;
	private OutputStreamWriter outputStreamWriter;
	private StartupInitializer gui;



	public NetworkClient(StartupInitializer gui, String ip, int port, String Username, String passwort) {
		this.setName("NetworkReceiverThread");
		this.ip = ip;
		this.gui = gui;
		this.port = port;
		this.username = Username;
		try {
			this.pw = HashText.sha1(passwort);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		connect();
	}

	private void connect() {
		try {
			Socket connection = new Socket(ip, port);
			OutputStream outputStream = connection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			connected = true;
			new SendThread().start();
//			gui.setConnectionOK();
			
			JSONObject loginMessage = new JSONObject();
			loginMessage.put("type", "login");
			loginMessage.put("user", username);
			loginMessage.put("pw", pw);
			this.send(loginMessage.toJSONString());
			
//			this.send("{\"type\":\"login\", \"user\":\"karl\"}");
		
			while (connected) {
				String read = in.readLine();
				if(read == null){
					break;
				}
				System.out.println(read);
//				gui.processMessage(read);
			}
			outputStreamWriter.close();
			outputStream.close();
			in.close();
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
			gui.setConnectionError(e.getMessage());
//			gui.setConnectionError(e.getMessage());
		}
		

	}

	LinkedBlockingQueue<String> sendingQueue = new LinkedBlockingQueue<String>();

	
	public void send(String what) {
		try {
			sendingQueue.put(what);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class SendThread extends Thread {
		@Override
		public void run() {
			this.setName("NetworkSenderThread");
			//sendingQueue.clear();
			while (connected) {
				try {
					String what = sendingQueue.take();
					outputStreamWriter.write(what + "\n");
					outputStreamWriter.flush();
					System.out.println("sent message:" + what);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					connected = false;
//					gui.setConnectionError(e.getMessage());

				}
			}
		}
	}
	

	public void disconnect() {
		connected = false;
	}

	public boolean isConnected() {
		return connected;
	}

}
