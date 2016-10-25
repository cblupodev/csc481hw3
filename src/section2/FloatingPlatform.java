package section2;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;
import processing.core.PApplet;
import section1.Time;

public class FloatingPlatform extends Movable implements GameObject {
	
	public float width;
	float windowWidth;
	public Time time;
	private long lastTick = -1;
	
	public FloatingPlatform(int windowWidth, int windowHeight) {
		this.shape = new float[] {-1000, windowHeight*.7f, windowWidth * .2f, windowHeight*.025f};
		this.width = windowWidth * .2f;
		this.windowWidth = (float) windowWidth;
	}
	
	@Override
	public FloatingPlatform update() {
		if (lastTick == -1) { 
			lastTick = time.getTime(); // initialize the tick
		}
		// do nothing if the ticks are the same
		if (lastTick != time.getTime()) {
			lastTick = time.getTime();
			shape[0] -= 1;
			if (shape[0] + width < 0) {
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
		// TODO Auto-generated method stub
		
	}

}
