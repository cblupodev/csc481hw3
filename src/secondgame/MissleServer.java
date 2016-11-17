package secondgame;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;

public class MissleServer extends Movable implements GameObject {
	
	public int id;
	
	public MissleServer(float x, float y) {
		this.shape = new float[] {x, y, 10, 20};
		this.type = "rectangle";
	}
	
	public MissleServer update() {
		initializeTick();
		if (continueUpdate() == true) {
			float f= (float)diff / movementFactor;
			shape[1] -= f; // move the missle up the screen
		}
		return this;
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub

	}

}
