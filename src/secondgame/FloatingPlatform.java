package secondgame;

import java.io.File;
import java.util.Arrays;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import processing.core.PApplet;

public class FloatingPlatform extends Movable implements GameObject {
	
	public float width;
	public float windowWidth;
	
	public FloatingPlatform(int windowWidth, int windowHeight, String scriptFileName) {
		this.shape = new float[] {-1000, windowHeight*.7f, windowWidth * .2f, windowHeight*.025f};
		this.width = windowWidth * .2f;
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
	
	@Override
	public FloatingPlatform update() {
		initializeTick();
		
		// do nothing if the ticks are the same
		if (continueUpdate() == true) {
			if (scriptFileName != null) {
				scripts.executeScript();
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

	@Override
	public void onEvent(Event e) {
		
	}

}