package secondgame;

import java.util.ArrayList;

import gameobjectmodel.GameObject;

public class EnemyColumn implements GameObject {
	
	public ArrayList<Enemy> enemyColumn = new ArrayList<>();
	
	public void update() {
		if (enemyColumn.size() > 0) {
			enemyColumn.get(enemyColumn.size() - 1).update(true); // update the one on the end of the list. tell it it can fire
		}
		for (Enemy e : enemyColumn) {
			e.update(false);
		}
	}

	@Override
	public void onEvent(Event e) {

	}

	public void add(Enemy enemy) {
		enemyColumn.add(enemy);
	}

}
