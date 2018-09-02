package smithies.textadventure.rooms;

public class Hall extends Room {

    private RoomName name = RoomName.HALL;
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
        output.displayTextLines("To the north, the hallway continues.", "To the east is a closed door.",
                "To the west is an open door.");
    }

    @Override
    public RoomName goNorth() {
        return RoomName.KITCHEN;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.KITCHEN;
    }

    @Override
    public RoomName goEast() {
        return RoomName.KITCHEN;
    }

    @Override
    public RoomName goWest() {
        return RoomName.KITCHEN;
    }
}
