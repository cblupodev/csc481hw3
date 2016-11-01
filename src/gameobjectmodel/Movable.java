package gameobjectmodel;

import processing.core.PApplet;
import section2.Server;

// for objects that move

public class Movable implements Component {
	
	public String type; // the object type to use for collisions
	public float[] shape; // the object shape to use for drawing and updating
	public long lastTick = -1; // keep track of the last tick to calculate how much to move between frames
	public long lastReplayTick = -1;
	
	// have a drawing component
	transient private Drawing drawing = new Drawing();
	
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
		if (lastReplayTick == -1) {
			try {
				lastReplayTick = Server.replay.time.getTime();
			} catch (NullPointerException e) {
				// dont do anything if havent started recording yet
			}
		}
	}
	
	public long diff = 0;
	public boolean continueUpdate() {
		if (Server.replay.isInReplayMode == false) {
			long getTime = Server.gametime.getTime();
			if (lastTick != getTime) {
				diff = getTime - lastTick;
				lastTick = getTime;
				return true;
			}	
		}
		if (Server.replay.isInReplayMode == true) {
			long getReplayTime = Server.replay.time.getTime();
			if (lastReplayTick != getReplayTime) {
				diff = getReplayTime - lastReplayTick;
				lastReplayTick = getReplayTime;
				return true;
			}
		}
		return false;
	}
}
