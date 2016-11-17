package secondgame;

import java.util.Arrays;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import gameobjectmodel.Physics;
import processing.core.PApplet;

// The server version of character
public class CharacterServer extends Movable implements GameObject {

	public float originalX; // original x position
	public float originalY; // original y position
	public int[] color; // character color
	private int windowHeight; // sketch height
	public int id; // identifier used for event management

	private Physics physics = new Physics(); // keep reference to physics so it can update the character
	public EventManager events;

	public CharacterServer(int id, int windowWidth, int windowHeight, EventManager events, Physics physics2, String scriptFileName) {
		this.id = id;
		this.type = "rect";
		this.shape = new float[] { windowWidth * .1f, windowHeight - 50, 25, 50 };
		this.originalX = shape[0];
		this.originalY = shape[1];
		//this.originalX = windowWidth * .5f;
		//this.originalY = windowHeight * .1f;
		// make it a triangle
		//this.shape = new float[] {originalX, originalY, 
			//					  originalX + (windowWidth * .05f), originalY - (windowHeight * .05f), 
				//				  originalX + (windowWidth * .1f), originalY };
		this.windowHeight = windowHeight;
		this.events = events;
		this.physics = physics2;
		try {
			if(scriptFileName != null) { 
			    this.scriptFileName = scriptFileName;
				scripts.loadScript(scriptFileName);
				scripts.bindArgument("game_object", this);
			}
		} catch (Exception e) {
		}
	}
	
	public CharacterServer update() {
		return this;
	}

	// send keyboard inputs to server
	public boolean updateInput(String message) {
		initializeTick();
		long diffTotal = 0;
		if (continueUpdate() == true) {
			float f= (float)diff / movementFactor;
			if (message.equals("LEFT")) {
				shape[0] -= f; // move x position left
				diffTotal += diff;
				return true;
			}
			if (message.equals("RIGHT")) {
				shape[0] += f; // move x position left
				diffTotal += diff;
				return true;
			}
			if (message.equals("SPACE")) {
				// TODO create a missile
				return true;
			}
		}
		return false;
	}

	public void draw(PApplet p) {
		setParent(p);
		getDrawing().drawFill(this.color);
		getDrawing().drawRect(this.shape);
	}

	public Physics getPhysics() {
		if (physics == null) {
			this.physics = new Physics();
		}
		return this.physics;
	}

	// set a character to its spawn position and state
	public void setToSpawnPoint() {
		shape[0] = originalX;
		shape[1] = originalY;
	}
	
	// need this so the script can have access to creatig new events
	public void createNewEvent(String type, int age, int priority) {
		events.addEvent(new Event(type, null, age, priority));
	}

	@Override
	public void onEvent(Event e) {
		if (scriptFileName != null) {
			scripts.bindArgument("e", e);
			scripts.executeScript();
		}
		else {
			if (e.type.equals("keyboard,"+id)) {
				String s = (String) e.parameters;
				updateInput(s);
				update();
			} else if (e.type.equals("collision,"+id)) {
				events.addEvent(new Event("death,"+id, null, 1, 0));
			} else if (e.type.equals("death,"+id)) {
				events.addEvent(new Event("spawn,"+id, null, 2, 0));
			} else if (e.type.equals("spawn,"+id)) {
				setToSpawnPoint();
			} else {
				System.out.println("didnt find an event type");
			}
		}
	}
}
