package section2;

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
		shape[0] -= 1;
		if (shape[0] + width < 0) {
			shape[0] = windowWidth;
		}
		return this;
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
