package smithies.textadventure.rooms;

public class HallSouth extends Room {

    private RoomName name = RoomName.HALL_SOUTH;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public String[] getFullDescriptionLines() {
        return new String[]{"There is a rug on the floor and a wooden shelf by the wall",
                "To the north, the hallway continues.", "To the east is a closed door.",
                "To the south is a closed door.", "To the west is an open door."};
    }

    @Override
    public RoomName goNorth() {
        return RoomName.HALL_MIDDLE;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.LOCKED_DOOR;
    }

    @Override
    public RoomName goEast() {
        return RoomName.CLOSED_PUSH_DOOR;
    }

    @Override
    public RoomName goWest() {
        return RoomName.KITCHEN_SOUTH;
    }
}
