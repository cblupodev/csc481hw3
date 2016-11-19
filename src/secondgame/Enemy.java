package secondgame;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import processing.core.PApplet;

public class Enemy extends Movable implements GameObject {
	
	public float width;
	public float windowWidth;
	public boolean missleInFlight = false;
	
	public Enemy(int windowWidth, int windowHeight, String scriptFileName) {
		this.shape = new float[] {-1000, windowHeight*.2f, windowWidth * .05f, windowHeight*.015f};
		this.width = shape[2];
		this.windowWidth = (float) windowWidth;
		try {
			if(scriptFileName != null) { 
			    this.scriptFileName = scriptFileName;
				scripts.loadScript(scriptFileName);
				scripts.bindArgument("game_object", this);
			}
		} catch (Exception e) {
		}
	}
	
	public Enemy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Enemy update(boolean columnEnd) {
		initializeTick();
		
		// do nothing if the ticks are the same
		if (continueUpdate() == true) {
			if (scriptFileName != null) {
				scripts.executeScript(columnEnd);
			}
			else {
				shape[0] -= (float) diff / (movementFactor * 2); // the division just makes it arbitrarialy run a little slower
				if (shape[0] + width < 0) { // wrap around to the others side
					shape[0] = windowWidth;
				}
			}
			return this;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "FloatingPlatform [width=" + width + ", windowWidth=" + windowWidth + ", scriptFileName="
				+ scriptFileName + ", type=" + type + ", shape=" + Arrays.toString(shape) + ", lastTick=" + lastTick
				+ ", movementFactor=" + movementFactor + ", scripts=" + scripts
				+ ", diff=" + diff + "]";
	}

	public void draw(PApplet p) {
		setParent(p);
		getDrawing().drawRect(shape);
	}
	
	public void fireMissle() {
		if (shouldFireMissle()) {
			Server.missles.add(new MissleServer(shape[0] - 10, shape[1] + (shape[3] + 2), false));
			missleInFlight = true;
		}
	}
	
	// just randomly fire a missle
	Random rand = new Random();
	int select;
	public boolean shouldFireMissle() {
		select = rand.nextInt(500);
		return select == 1;
	}

	@Override
	public void onEvent(Event e) {
		
	}

}
