package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.session.AllRooms;
import smithies.textadventure.ui.DisplayConsoleOutput;

public class GoDirection implements GameCommandState {

    private DisplayConsoleOutput output = new DisplayConsoleOutput();

    private Player player;
    private Adverb direction;
    private AllRooms allRooms;

    public GoDirection(Player player, Adverb direction, AllRooms allRooms) {
        this.player = player;
        this.direction = direction;
        this.allRooms = allRooms;
    }

    @Override
    public void run() {
        RoomName roomName = player.goDirection(direction).orElseThrow(() -> {
            return new RuntimeException("Invalid direction supplied");
        });
        handleRoomName(player, allRooms, roomName);
    }

    private void handleRoomName(Player player, AllRooms allRooms, RoomName roomName) {
        if (roomName.isValidRoom()) {
            handleValidRoom(player, allRooms, roomName);
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

    private void handleValidRoom(Player player, AllRooms allRooms, RoomName roomName) {
        player.setCurrentRoom(allRooms.get(roomName));
        player.enterRoom();
    }
}
