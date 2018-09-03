package smithies.textadventure.command;

import smithies.textadventure.item.ItemName;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.session.AllRooms;
import smithies.textadventure.session.Player;
import smithies.textadventure.ui.DisplayConsoleOutput;

public class CommandInterpretter {

    private DisplayConsoleOutput output = new DisplayConsoleOutput();

    public void processCommand(Player player, AllRooms allRooms, UserInputCommand command) {
        //TODO: use switch statement
        if (UserInputCommand.EXIT.equals(command)) {
            System.exit(0);
        } else if (UserInputCommand.WAIT.equals(command)) {
            output.displayTextLine("You scratch your ears with your leg");
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
        } else if (UserInputCommand.TAKE.equals(command)) {
            if (player.canTakeItem(ItemName.TENNIS_BALL)) {
                player.takeItem(ItemName.TENNIS_BALL);
                output.displayTextLine("Taken.");
            }
        } else if (UserInputCommand.INVENTORY.equals(command)) {
            player.viewInventory();
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
