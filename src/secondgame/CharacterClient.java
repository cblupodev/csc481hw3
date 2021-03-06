package secondgame;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import gameobjectmodel.Physics;
import processing.core.PApplet;

// The client version of Character
public class CharacterClient extends Movable implements GameObject {
	public float originalX; // original x position
	public float originalY; // original y position
	public int[] color; // character color
	private int windowHeight; // sketch height
	public int id;

	private Physics physics = new Physics(); // keep reference to physics so it can update the character

	public CharacterClient(int id, int windowWidth, int windowHeight) {
		this.id = id;
		this.type = "rect";
		this.shape = new float[] { windowWidth * .1f, windowHeight * .9f - 50, 25, 50 };
		this.originalX = shape[0];
		this.originalY = shape[1];
		this.windowHeight = windowHeight;
	}

	public CharacterClient update() {
		// redraw the agent if it's in the process of jumping
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
		shape[0] = originalX;
		shape[1] = originalY;
	}

	@Override
	public void onEvent(Event e) {
	}
}
