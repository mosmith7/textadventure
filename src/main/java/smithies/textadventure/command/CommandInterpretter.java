package smithies.textadventure.command;

import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.session.AllRooms;
import smithies.textadventure.session.Player;
import smithies.textadventure.ui.DisplayConsoleOutput;

public class CommandInterpretter {

    private DisplayConsoleOutput displayConsoleOutput = new DisplayConsoleOutput();

    public void processCommand(Player player, AllRooms allRooms, UserInputCommand command) {
        if (UserInputCommand.EXIT.equals(command)) {
            System.exit(0);
        } else if (UserInputCommand.WAIT.equals(command)) {
            displayConsoleOutput.displayTextLine("You scratch your ears with your leg");
        } else if (UserInputCommand.LOOK.equals(command)) {
            player.look();
        } else if (UserInputCommand.NORTH.equals(command)) {
            RoomName roomName = player.goNorth();
            handleRoomName(player, allRooms, roomName);
        } else if (UserInputCommand.EAST.equals(command)) {
            RoomName roomName = player.goEast();
            handleRoomName(player, allRooms, roomName);
        } else if (UserInputCommand.SOUTH.equals(command)) {
            RoomName roomName = player.goSouth();
            handleRoomName(player, allRooms, roomName);
        } else if (UserInputCommand.WEST.equals(command)) {
            RoomName roomName = player.goWest();
            handleRoomName(player, allRooms, roomName);
        }
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
            displayConsoleOutput.displayClosedDoorResponse();
        } else if (RoomName.CLOSED_PUSH_DOOR.equals(roomName)) {
            displayConsoleOutput.displayClosedDoorResponse();
        } else if (RoomName.CLOSED_PULL_DOOR.equals(roomName)) {
            displayConsoleOutput.displayClosedDoorResponse();
        } else if (RoomName.DEADEND.equals(roomName)) {
            displayConsoleOutput.displayTextLine("You walk over to the wall and curl up in a ball");
        }
    }

    private void handleValidRoom(Player player, AllRooms allRooms, RoomName roomName) {
        player.setCurrentRoom(allRooms.get(roomName));
        player.enterRoom();
    }
}
