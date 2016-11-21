package gameobjectmodel;

import processing.core.PApplet;
import secondgame.Enemy;
import secondgame.ScriptManager;
import secondgame.Server;

// for objects that move

public class Movable implements Component {
	
	public String type; // the object type to use for collisions
	public float[] shape; // the object shape to use for drawing and updating
	public long lastTick = -1; // keep track of the last tick to calculate how much to move between frames
	public float movementFactor = 2; // controls the magnitude of movement, the lower the number the more magnitude
	public String scriptFileName = null; 
	
	// have a drawing component
	transient private Drawing drawing = new Drawing();
	
	protected transient ScriptManager scripts = new ScriptManager();
	
	// update object position and stuff
	// this should be overwritten
	public Movable update() {
		return this;
	}

	// draw the object
	// this should be overwritten
	public void draw(PApplet p) { 
		
	}

	// make sure the drawing component has  reference to the sketch
	public void setParent(PApplet parent) {
		if (getDrawing().parent == null) {
			drawing.parent = parent;
		}
	}
	
	// make sure drawing is instantiated
	// I ran into problems with a null drawing component when passing info between client and server
	public Drawing getDrawing() {
		if (drawing == null) {
			this.drawing = new Drawing();
		}
		return this.drawing;
	}
	
	public void initializeTick() {
		long getTime = Server.gametime.getTime();
		if (lastTick == -1) {
			lastTick = getTime;
		}
	}
	
	public long diff = 0;
	public boolean continueUpdate() {
		long getTime = Server.gametime.getTime();
		if (lastTick != getTime) {
			diff = getTime - lastTick;
			lastTick = getTime;
			return true;
		}	
		return false;
	}

	public Enemy update(boolean columnEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enemy update(boolean columnEnd, float firstX) {
		// TODO Auto-generated method stub
		return null;
	}
}
