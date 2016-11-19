function update() {
	game_object.shape[0] -= parseFloat(game_object.diff) / (game_object.movementFactor * 2);
	if ((game_object.shape[0] + game_object.width ) < 0) { // wrap around to the others side
		game_object.shape[0] = game_object.windowWidth;
	}
	//if (game_object.shouldFireMissle()) {
	game_object.fireMissle();
	//}
}