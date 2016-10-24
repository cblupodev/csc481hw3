package section2;

import java.util.Arrays;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import gameobjectmodel.Physics;
import processing.core.PApplet;
import section1.Time;

public class CharacterServer extends Movable implements GameObject {

	public float originalX; // original x position
	public float originalY; // original y position
	public int[] color; // character color
	public boolean jumping = false; // is it jumping?
	public float jumpingAngle = 180f; // the jumping angle
	private int windowHeight; // sketch height
	public Time time;
	public int id;

	private Physics physics = new Physics(); // keep reference to physics so it can update the character
	public EventManager events;

	public CharacterServer(int id, int windowWidth, int windowHeight, Time time, EventManager events, Physics physics2) {
		this.id = id;
		this.type = "rect";
		this.shape = new float[] { windowWidth * .1f, windowHeight * .9f - 50, 25, 50 };
		this.originalX = shape[0];
		this.originalY = shape[1];
		this.windowHeight = windowHeight;
		this.time = time;
		this.events = events;
		this.physics = physics2;
	}

	public CharacterServer update() {
		// redraw the agent if it's in the process of jumping
		if (jumping) {
			// used that colliding circles example from processing.org
			float newY = windowHeight * .9f - 50 + (200 * physics.sinWrap(physics.radiansWrap(jumpingAngle)));
			shape[1] = newY;// set a new y position
			jumpingAngle = jumpingAngle + 3; // increment the jumping angle
			if (jumpingAngle == 360) { // stop jumping if reached the ground
				jumping = false;
				jumpingAngle = 180;
				shape[1] = originalY;
			}
		}
		return this;
	}

	// send keyboard inputs to server
	private boolean updateInput(String message) {
		if (message.equals("LEFT")) {
			shape[0] -= 5; // move x position left
			return true;
		}
		if (message.equals("RIGHT")) {
			shape[0] += 5; // move x position right
			return true;
		}
		if (message.equals("SPACE")) {
			if (jumping == false) {
				jumping = true;
			}
			return true;
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
		jumping = false;
		shape[0] = originalX;
		shape[1] = originalY;
	}

	@Override
	public void onEvent(Event e) {
		if (e.type.equals("keyboard,"+id)) {
			String s = (String) e.parameters;
			updateInput(s);
		} else if (e.type.equals("collision,"+id)) {
			events.addEvent(new Event("death,"+id, null, time.getTime(), 1, 0));
		} else if (e.type.equals("death,"+id)) {
			events.addEvent(new Event("spawn,"+id, null, time.getTime() - 100000000, 2, 0));
		} else if (e.type.equals("spawn,"+id)) {
			setToSpawnPoint();
		} else {
			System.out.println("didnt find an event type");
		}
	}
}
