package secondgame;

import java.util.ArrayList;

import gameobjectmodel.GameObject;

public class EnemyColumn implements GameObject {
	
	public ArrayList<Enemy> enemyColumn = new ArrayList<>();
	
	public EnemyColumn (int numInColumn, int windowWidth, int windowHeight, float initialX, String script1) {
		float initialY = windowHeight*.2f;
		for (int j = 0; j < numInColumn; j++) {
			enemyColumn.add(new Enemy(windowWidth, windowHeight, initialX, initialY, script1));
			initialY += windowHeight*.2;
		}
	}
	
	public void update(float firstX) {
		Enemy enemyAtTheEnd = null;
		if (enemyColumn.size() > 0) {
			enemyColumn.get(enemyColumn.size() - 1).update(true, firstX); // update the one on the end of the list. tell it it can fire
			enemyAtTheEnd = enemyColumn.get(enemyColumn.size() - 1);
		}
		for (Enemy e : enemyColumn) {
			e.shape[0] = enemyAtTheEnd.shape[0]; // make all enemies in the column line up on the x axis
		}
	}

	@Override
	public void onEvent(Event e) {

	}

	public void add(Enemy enemy) {
		enemyColumn.add(enemy);
	}

}
