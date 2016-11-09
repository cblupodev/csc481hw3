package section2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import gameobjectmodel.Drawing;
import gameobjectmodel.GameObject;
import processing.core.PApplet;
import section1.Time;

public class Client extends PApplet implements GameObject {
	
	private static final int PORT = 6789; // socket port
	private Drawing drawing = null;
	private PrintWriter writer = null; // output stream
	private BufferedReader reader = null; // input stream
	private Socket socket = null;
	private String address = ""; // socket address
	private ServerClientMessage lastMessage; // remember the last address that was send from the client, so don't get NPE
	private ArrayList<CharacterClient> characters = new ArrayList<>(); // list of characters to display to the screen
	private int windowWidth;
	private int windowHeight;
	private float[] rectFoundation1;
	private float[] rectFoundation2;
	private int id;
	
	private Gson gson; // google json parser
	private Type ServerClientMessageType; // type for gson parsing
	private Type ServerClientInitializationMessageType; // type for json parsing
	private Type EventType;

	// start the program
	public static void main(String[] args) {
		Client c = new Client();
		c.address = args[0];
		PApplet.main("section2.Client");
	}

	
	public void settings() {
		try {
			drawing = new Drawing(this);
			gson  = new Gson();
	        ServerClientMessageType = new TypeToken<ServerClientMessage>() {}.getType();
	        ServerClientInitializationMessageType = new TypeToken<ServerClientInitializationMessage>() {}.getType();
	        EventType = new TypeToken<Event>() {}.getType();
			
	        // create socket and get streams
			socket = new Socket(address, PORT);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		initializeFinalValues();
		size(windowWidth, windowHeight); // set the window dimensions
	}
	
	public void setup() {
		fill(120,50,240);
	}
	
	// this should only read the first message the server ever sends
	// Initialize all the values that: 
	// the client uses that are similar to the server
	// don't change
	// this is done so I don't have duplicated code on the server and client
	private void initializeFinalValues() {
		String i = "";
		try {
				i = reader.readLine();
				ServerClientInitializationMessage initMessage = gson.fromJson(i,ServerClientInitializationMessageType);
				rectFoundation1 = initMessage.rectFoundation1;
				rectFoundation2 = initMessage.rectFoundation2;
				windowWidth = initMessage.windowWidth;
				windowHeight = initMessage.windowHeight;
				id = initMessage.id;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	FloatingPlatform fp = new FloatingPlatform(windowWidth, windowHeight); // keep reference so not allocating memory each time
	CharacterClient c; // keep reference so not allocating memory each time
	public void draw() {
		// read the character object from the server. the server does the updating
		ServerClientMessage message;
		sendInputToServer();
		message = readMessageFromServer();
		// render -->
		background(0); // reset the background each frame
		drawing.drawFill(new int[] { 221, 221, 221 }); // light gray
		drawing.drawRect(rectFoundation1);
		drawing.drawRect(rectFoundation2);
		drawing.drawFill(new int[] { 50, 50, 50 }); // light gray
		try {
			fp.shape = message.floatPlatformShapeMessage;
			fp.draw(this);
			for (int i = 0; i < message.cShapes.size(); i++) { // draw the characters
				if (message.cShapes.size() > characters.size()) { // if a new client connected and thus character added, then add to the list
					CharacterClient c = new CharacterClient(characters.size() - 1, windowWidth, windowHeight); // new id will always be the size - 1
					characters.add(c);
				}
				// update the characters
				c = characters.get(i);
				c.shape = message.cShapes.get(i);
				c.jumping = message.cJumping.get(i);
				c.jumpingAngle = message.cjumpingAngle.get(i);
				c.color = message.cColor.get(i);
				c.draw(this);
			}
		} catch (NullPointerException e) { }
	}
	
	// send keyboard input to the server so it can update character
	private void sendInputToServer() {
		String out = "";
		if (keyPressed) { // move the agent if the key is pressed
			if (keyCode == LEFT) {
				out  = "LEFT";
			}
			if (keyCode == RIGHT) {
				out = "RIGHT";
			}
			if (key == ' ') { // begin jumping
				out = "SPACE";
			}
			if (key == 'r') {
				out = "r";
			}
			if (key == 's') {
				out  = "s";
			}
			if (out.isEmpty() == false) {
				out = gson.toJson(new Event("keyboard,"+id, out, 0, 1), EventType);
				writer.println(out);
			}
		}
		writer.flush();
	}

	// read an updated message from the server
	private ServerClientMessage readMessageFromServer() {
		try {
			if (reader.ready()) {
				String i = reader.readLine();
				ServerClientMessage message = gson.fromJson(i,ServerClientMessageType);
				lastMessage = message;
				return message;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lastMessage;
	}


	@Override
	public void onEvent(Event e) {
		
	}
	


}
