function update(columnEnd, firstX) {
	if (columnEnd === true) {
		game_object.shape[0] -= parseFloat(game_object.diff) / (game_object.movementFactor * 6);
		game_object.fireMissle();
	} else {
		if (isNaN(firstX) === false) {
			game_object.shape[0] = parseFloat(firstX); //parseFloat(game_object.diff) / (game_object.movementFactor * 2);
		}
	}
	if ((game_object.shape[0] + game_object.width ) < 0) { // wrap around to the others side
		game_object.shape[0] = game_object.windowWidth;
	}
}