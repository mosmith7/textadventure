package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.map.DungeonMap;

public class GoDirection implements GameCommandState {

    private Player player;
    private Adverb direction;
    private DungeonMap map;

    public GoDirection(Player player, Adverb direction, DungeonMap map) {
        this.player = player;
        this.direction = direction;
        this.map = map;
    }

    @Override
    public void run() {
        player.goDirection(direction).ifPresent(roomName -> {
            player.setCurrentRoom(map.get(roomName));
            player.enterRoom();
        });
    }
}
