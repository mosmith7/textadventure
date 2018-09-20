package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.session.AllRooms;
import smithies.textadventure.ui.DisplayConsoleOutput;

public class GoDirection implements GameCommandState {

    private DisplayConsoleOutput output = new DisplayConsoleOutput();

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
        RoomName roomName = player.goDirection(direction).orElseThrow(() -> {
            return new RuntimeException("Invalid direction supplied");
        });
        handleRoomName(player, map, roomName);
    }

    private void handleRoomName(Player player, DungeonMap map, RoomName roomName) {
        if (roomName.isValidRoom()) {
            handleValidRoom(player, map, roomName);
        } else {
            handleInvalidRoom(roomName);
        }
    }

    private void handleInvalidRoom(RoomName roomName) {
        if (RoomName.LOCKED_DOOR.equals(roomName)) {
            output.displayClosedDoorResponse();
        } else if (RoomName.CLOSED_PUSH_DOOR.equals(roomName)) {
            output.displayClosedDoorResponse();
        } else if (RoomName.CLOSED_PULL_DOOR.equals(roomName)) {
            output.displayClosedDoorResponse();
        } else if (RoomName.DEADEND.equals(roomName)) {
            output.displayTextLine("You walk over to the wall and curl up in a ball");
        }
    }

    private void handleValidRoom(Player player, DungeonMap map, RoomName roomName) {
        player.setCurrentRoom(map.get(roomName));
        player.enterRoom();
    }
}
