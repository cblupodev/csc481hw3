package secondgame;

import gameobjectmodel.GameObject;
import gameobjectmodel.Movable;

public class MissleServer extends Movable implements GameObject {
	
	public int id;
	boolean friend = true;
	
	public MissleServer(float x, float y, boolean friend) {
		this.shape = new float[] {x, y, 10, 20};
		this.type = "rect";
		this.movementFactor = this.movementFactor/2;
		this.friend = friend;
	}
	
	public MissleServer update() {
		initializeTick();
		if (continueUpdate() == true) {
			float f= (float)diff / movementFactor;
			if (friend == true) {
				shape[1] -= f; // move the missle up the screen
			} else  {
				shape[1] += f; // move missle down the screen
			}
		}
		return this;
	}

	@Override
	public void onEvent(Event e) {
		// TODO Auto-generated method stub

	}

}
