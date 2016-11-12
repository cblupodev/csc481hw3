package scripting;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import processing.core.PApplet;

public class FloatingPlatform extends Movable implements GameObject {
	
	public float width;
	float windowWidth;
	
	public FloatingPlatform(int windowWidth, int windowHeight) {
		this.shape = new float[] {-1000, windowHeight*.7f, windowWidth * .2f, windowHeight*.025f};
		this.width = windowWidth * .2f;
		this.windowWidth = (float) windowWidth;
	}
	
	@Override
	public FloatingPlatform update() {
		initializeTick();
		
		// do nothing if the ticks are the same
		if (continueUpdate() == true) {
			shape[0] -= (float) diff / (movementFactor * 2); // the division just makes it arbitrarialy run a little slower
			if (shape[0] + width < 0) { // wrap around to the others side
				shape[0] = windowWidth;
			}
			return this;
		}
		return null;
	}
	
	public void draw(PApplet p) {
		setParent(p);
		getDrawing().drawRect(shape);
	}

	@Override
	public void onEvent(Event e) {
		
	}

}
