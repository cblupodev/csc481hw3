function update(message) {
	print("character serve script executing");
	if (message === "LEFT") {
		game_object.shape[0] -= parseFloat(game_object.diff) / game_object.movementFactor; // move x position left
		game_object.diffTotal += game_object.diff;
	}
	if (message === "RIGHT") {
		game_object.shape[0] += parseFloat(game_object.diff) / game_object.movementFactor; // move x position right
		game_object.diffTotal += game_object.diff;
	}
	if (message === "SPACE") {
		if (game_object.jumping == false) {
			game_object.jumping = true;
		}
	}
}