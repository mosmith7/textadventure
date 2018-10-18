package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.map.DungeonMap;

public class ClimbStairs implements GameCommandState {

    private Player player;
    private DungeonMap map;
    private Adverb verticalDirection;
    private Adverb upCompassDirection;
    private Adverb downCompassDirection;

    public ClimbStairs(Player player, DungeonMap map, Adverb verticalDirection,
                       Adverb upCompassDirection, Adverb downCompassDirection) {
        this.player = player;
        this.map = map;
        this.verticalDirection = verticalDirection;
        this.upCompassDirection = upCompassDirection;
        this.downCompassDirection = downCompassDirection;
    }

    @Override
    public void run() {
        switch (verticalDirection) {
            case UP:
                player.goToRoomInDirection(upCompassDirection).ifPresent(roomName -> {
                    player.setCurrentRoom(map.get(roomName));
                    player.enterRoom();
                });
                break;
            case DOWN:
                player.goToRoomInDirection(downCompassDirection).ifPresent(roomName -> {
                    player.setCurrentRoom(map.get(roomName));
                    player.enterRoom();
                });
                break;
            default:
                //TODO: handle
                break;
        }
    }
}
