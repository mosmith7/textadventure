package smithies.textadventure.command;

import smithies.textadventure.rooms.Room;
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
            case GO_TO_NOUN:
                Room currentRoom = player.getCurrentRoom();
                if (currentRoom.containsSearchable(command.getNoun())) {
                    currentRoom.goToSearchable(command.getNoun());
                } else {
                    output.displayTextLine("Are you sure " + command.getNoun() + " is in the same room as you?");
                }
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
            case CLIMB_UP:
                player.climbUp(command.getNoun());
                break;
            case CLIMB_DOWN:
                if (command.getNoun() != null) {
                    player.climbDown(command.getNoun());
                } else {
                    player.climbDown();
                }
                break;
            case TAKE:
                if (player.canTakeItem(command.getNoun())) {
                    player.takeItem(command.getAndResetNoun());
                    output.displayTextLine("Taken.");
                }
                break;
            case DROP:
                if (player.isInventoryEmpty()) {
                    output.displayTextLine("You have nothing to drop");
                } else {
                    if (command.getNoun() != null) {
                        player.inventoryPeek().ifPresent(itemName -> {
                            if (itemName.equals(command.getAndResetNoun())) {
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
                player.search(command.getAndResetNoun(), command.getAdverb());
                break;
            case SEARCH_IN:
                player.search(command.getAndResetNoun(), command.getAdverb());
                break;
            case SEARCH_ON:
                player.search(command.getAndResetNoun(), command.getAdverb());
                break;
            case BARK:
                output.displayTextLine("You bark");
                break;
            case WHINE:
                output.displayTextLine("You whine and squeek, trying to get attention");
                break;
            case GROWL:
                output.displayTextLine("You growl menacingly");
                break;
            case SCRATCH:
                if (command.getNoun() != null) {
                    output.displayTextLine("You scratch the " + command.getAndResetNoun().toString());
                } else {
                    output.displayTextLine("You scratch the floor");
                }
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
