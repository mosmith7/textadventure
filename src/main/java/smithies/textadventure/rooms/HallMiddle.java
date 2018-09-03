package smithies.textadventure.rooms;

public class HallMiddle extends Room {

    private RoomName name = RoomName.HALL_MIDDLE;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public void displayFullDescription() {
        displayName();
        output.displayTextLines("You are in a narrow hallway.",
                "To the north, the hallway continues.", "To the east is an open door.",
                "To the south the hallway continues.", "To the west is an closed door.");
    }

    @Override
    public RoomName goNorth() {
        return RoomName.HALL_NORTH;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.HALL_SOUTH;
    }

    @Override
    public RoomName goEast() {
        return RoomName.LIVING_ROOM;
    }

    @Override
    public RoomName goWest() {
        return RoomName.CLOSED_PUSH_DOOR;
    }
}
