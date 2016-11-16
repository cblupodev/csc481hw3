package secondgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gameobjectmodel.GameObject;
import gameobjectmodel.Immovable;
import gameobjectmodel.Movable;
import gameobjectmodel.Physics;
import processing.core.PApplet;

public class Server implements GameObject {
	
	public static int windowWidth = 600;
	public static int windowHeight = 400;
	
	// use the thread safe collections because they are being added and read from different threads
	public static CopyOnWriteArrayList<CharacterServer> characters = new CopyOnWriteArrayList<>(); // list of characters
	public static CopyOnWriteArrayList<BufferedReader> inStream = new CopyOnWriteArrayList<>(); // list of socket input streams
	public static CopyOnWriteArrayList<PrintWriter> outStream = new CopyOnWriteArrayList<>(); // list of socket output streams
	public FloatingPlatform floatingPlatform;
	private Physics physics;
	private EventManager events;
	private Time realtime;
	public static Time gametime;
	
	public static ArrayList<Immovable> immovables = new ArrayList<>(); // list of specific objects to collide with
	public static ArrayList<Movable> movables = new ArrayList<>(); // not used yet

	private Gson gson; // google json parser
	private Type ServerClientMessageType; // type for gson parsing
	private Type ServerClientInitializationMessageType; // type for json parsing
	private Type EventType;
	
	public Server(String script1) {
		floatingPlatform = new FloatingPlatform(windowWidth, windowHeight, script1);
	}

	public static void main(String[] args) {
		Server m = new Server(args[0]);
		m.run(args[1]);
		PApplet.main("csc481hw2.section2.Server");
	}
	
	public void run(String script2) {
		// add collidable stuff to the physics component
		events = new EventManager();
		physics = new Physics(events);
		realtime = new Time(null, 1, 0);
		// initialize the static objects
		Immovable rectFoundation1 = new Immovable("rect", new float[] {0, windowHeight*.9f, windowWidth*.75f, windowHeight*.1f});
		Immovable rectFoundation2 = new Immovable("line", new float[] {windowWidth - (windowWidth*.15f), windowHeight*.9f, windowWidth*.15f, windowHeight*.1f});
		Immovable boundaryLeft = new Immovable("line", new float[] {0, 0, 0, windowHeight});
		Immovable boundaryRight = new Immovable("line", new float[] {windowWidth, 0, windowWidth, windowHeight});
		Immovable rectPit = new Immovable("rect", new float[] {
				rectFoundation1.shape[2]+20, 
				rectFoundation1.shape[1],
				windowWidth - (rectFoundation1.shape[2]+rectFoundation2.shape[2]) - 20, 
				rectFoundation1.shape[3]
		});
		immovables.add(boundaryLeft);
		immovables.add(boundaryRight);
		immovables.add(rectPit);
		gson = new Gson();
		ServerClientInitializationMessageType = new TypeToken<ServerClientInitializationMessage>() {}.getType();
		ServerClientMessageType = new TypeToken<ServerClientMessage>() {}.getType();
		EventType = new TypeToken<Event>() {}.getType();

		
		// start the thread that accepts incoming connections
		Thread t = new Thread(new ServerAccept());
		t.start();
		
		gametime = new Time(realtime, 1000000, 0); // this should be on a millisecond timescale, 1000000
		
		// read from clients
		PrintWriter out = null;
		CharacterServer c;
		int y = 0;
		while (true) { // never stop looking
			for (int i = 0; i < inStream.size(); i++) { // iterate over the client streams
				while(outStream.size() != inStream.size()); // busy wait until they are the same size
				out = outStream.get(i);
					
				// initialize the agent if the number of streams and agents aren't the same size
				if (characters.size() != inStream.size()) { // add a character
					
					c = new CharacterServer(i, windowWidth, windowHeight, events, physics, script2);
					characters.add(i, c);
					events.register("keyboard,"+i, c);
					events.register("keyboard,"+i, this);
					events.register("collision,"+i, c);
					events.register("spawn,"+i, c);
					events.register("death,"+i, c);
					// select random character
					Random r = new Random();
					c.color = new int[] {r.nextInt(255), r.nextInt(255), r.nextInt(255)};
					
					// send the non changing values to the client
					ServerClientInitializationMessage scim = new ServerClientInitializationMessage();
					scim.rectFloat = floatingPlatform.shape;
					scim.rectFoundation1 = rectFoundation1.shape;
					scim.rectFoundation2 = rectFoundation2.shape;
					scim.windowWidth = windowWidth;
					scim.windowHeight = windowHeight;
					scim.id = i;
					out.println(gson.toJson(scim, ServerClientInitializationMessageType));
					out.flush();
				} else {
					c = characters.get(i);
					characters.set(i, readInputFromClient(i, c, inStream.get(i), out)); // read input from client
					// UPDATE
					if (gametime.advanced() == true) {
						if (events.isEmpty() == false) {
							events.handle();
						}
						physics.collision();
						//characters.set(i, c.update());
						floatingPlatform.update();
						physics.floatingPlatform = floatingPlatform; // update the platform in the physics component
						writeMessageToClient(createServerClientMessage(), out);
					}
				}
			}
		}
		
	}
	
	
	// write a message to the client
	// mostly including updated info to draw
	private void writeMessageToClient(ServerClientMessage serverClientMessage, PrintWriter writer) {
		writer.println(gson.toJson(serverClientMessage, ServerClientMessageType));
	}

	// read keyboard input from client
	private CharacterServer readInputFromClient(int i, CharacterServer c, BufferedReader r, PrintWriter writer) {
		try {
			if (r.ready()) {
				String message = r.readLine();
				Event e = gson.fromJson(message, EventType);
				boolean cont = true;
				String p = (String)e.parameters;
				if (cont == true) {
					events.addEvent(e);
					boolean keypressed = false;
					if (keypressed) {
						characters.set(i, c);
						return c;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return c;
	}

	ServerClientMessage message = new ServerClientMessage(); // only allocate memory once
	public ServerClientMessage createServerClientMessage() {
		message.cShapes.clear();
		message.cColor.clear();
		message.floatPlatformShapeMessage = floatingPlatform.shape;
		for (CharacterServer c : characters) {
			message.cShapes.add(c.shape);
			message.cColor.add(c.color);
		}
		return message;
	}

	@Override
	public void onEvent(Event e) {
	}
}