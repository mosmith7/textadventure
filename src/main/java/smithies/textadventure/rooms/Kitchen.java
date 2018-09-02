package smithies.textadventure.rooms;

import smithies.textadventure.command.UserInputCommand;

public class Kitchen extends Room {

    private RoomName name = RoomName.KITCHEN;
    private boolean isFirstEntrance = true;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public boolean isFirstEntrance() {
        return isFirstEntrance;
    }

    @Override
    public void enter() {
        if (isFirstEntrance) {
            displayFullDescription();
            isFirstEntrance = false;
        } else {
            displayName();
        }

    }

    @Override
    public void displayFullDescription() {
        displayName();
        output.displayTextLines("You are in the kitchen");
    }
    @Override
    public RoomName goNorth() {
        return RoomName.HALL;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.HALL;
    }

    @Override
    public RoomName goEast() {
        return RoomName.HALL;
    }

    @Override
    public RoomName goWest() {
        return RoomName.HALL;
    }
}
