function update(message) {
	if (e.type === ("keyboard," + game_object.id)) {
		var s = e.parameters.toString();
		game_object.updateInput(s);
		game_object.update();
	} else if (e.type === "character_collision," + game_object.id) {
		game_object.createNewEvent("death," + game_object.id, 1, 0);
	} 
	else if (e.type.split(',')[0] === "missle_collision") {
		game_object.missleInFlight = false;
		game_object.removeMissleFromServer(parseInt(e.type.split(',')[1]));
	}
	else if (e.type === "death" + game_object.id) {
		game_object.createNewEvent("spawn," + game_object.id, 2, 0);
	} else if (e.type === "spawn," + game_object.id) {
		game_object.setToSpawnPoint();
	} else {
		print("didn't find an event type");
	}
}