package smithies.textadventure.rooms;

public class HallNorth extends Room {

    private RoomName name = RoomName.HALL_NORTH;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public void displayFullDescription() {
        displayName();
        output.displayTextLines("There is a rug on the floor and a wooden shelf by the wall",
                "To the north, the hallway continues.", "To the east is a closed door.",
                "To the south is a closed door.", "To the west is an open door.");
    }

    @Override
    public RoomName goNorth() {
        return RoomName.KITCHEN_SOUTH;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.KITCHEN_SOUTH;
    }

    @Override
    public RoomName goEast() {
        return RoomName.KITCHEN_SOUTH;
    }

    @Override
    public RoomName goWest() {
        return RoomName.KITCHEN_SOUTH;
    }
}
