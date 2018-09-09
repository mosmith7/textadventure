package smithies.textadventure.command;

import smithies.textadventure.item.ItemName;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.session.AllRooms;
import smithies.textadventure.session.Player;
import smithies.textadventure.ui.DisplayConsoleOutput;

public class CommandHandler {

    private DisplayConsoleOutput output = new DisplayConsoleOutput();

    public void processCommand(Player player, AllRooms allRooms, GameCommand command) {
        switch (command) {
            case EXIT:
                System.exit(0);
                break;
            case WAIT:
                output.displayTextLine("You scratch your ears with your leg");
                break;
            case LOOK:
                player.look();
                break;
            case NORTH:
                handleRoomName(player, allRooms, player.goNorth());
                break;
            case EAST:
                handleRoomName(player, allRooms, player.goEast());
                break;
            case SOUTH:
                handleRoomName(player, allRooms, player.goSouth());
                break;
            case WEST:
                handleRoomName(player, allRooms, player.goWest());
                break;
            case TAKE:
                if (player.canTakeItem(command.getNoun())) {
                    player.takeItem(command.getNoun());
                    output.displayTextLine("Taken.");
                }
                break;
            case DROP:
                if (player.isInventoryEmpty()) {
                    output.displayTextLine("You have nothing to drop");
                } else {
                    if (command.getNoun() != null) {
                        player.inventoryPeek().ifPresent(itemName -> {
                            if (itemName.equals(command.getNoun())) {
                                player.dropItem(itemName);
                                output.displayTextLine("Dropped.");
                            }
                        });
                    } else {
                        player.inventoryPeek().ifPresent(itemName -> {
                            player.dropItem(itemName);
                            output.displayTextLine("Dropped " + itemName.name());
                        });
                    }
                }
                break;
            case INVENTORY:
                player.viewInventory();
                break;
            case SEARCH_UNDER:
                player.search(command.getNoun(), command.getAdverb());
                break;
            case SEARCH_IN:
                player.search(command.getNoun(), command.getAdverb());
                break;
            case SEARCH_ON:
                player.search(command.getNoun(), command.getAdverb());
                break;
            case FAILED_TO_PARSE:
                output.displayTextLine("I did not understand your command");
                break;
            default:
                break;

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
