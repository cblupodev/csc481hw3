package secondgame;

import gameobjectmodel.Movable;

public class EnemyServer extends Movable {

	private int id;
	private float originalX;
	private float originalY;

	public EnemyServer(int id, int windowWidth, int windowHeight, float x, float y) {
		this.id = id;
		this.type = "rect";
		this.shape = new float[] { x, y, 10, 20 };
		this.originalX = shape[0];
		this.originalY = shape[1];
	}

}
