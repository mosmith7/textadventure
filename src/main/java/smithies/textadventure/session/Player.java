package smithies.textadventure.session;

import smithies.textadventure.command.CommandInterpretter;
import smithies.textadventure.command.UserInputCommand;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class Player {

    private Room currentRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void enterRoom() {
        this.currentRoom.enter();
    }

    public RoomName goNorth() {
        return this.currentRoom.goNorth();
    }

    public RoomName goSouth() {
        return this.currentRoom.goSouth();
    }

    public RoomName goEast() {
        return this.currentRoom.goEast();
    }

    public RoomName goWest() {
        return this.currentRoom.goWest();
    }

    public void look() {
        currentRoom.displayFullDescription();
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
