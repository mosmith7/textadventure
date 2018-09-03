package smithies.textadventure.rooms;

public class HallNorth extends Room {

    private RoomName name = RoomName.HALL_NORTH;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public String[] getFullDescriptionLines() {
        return new String[]{"There is a rug on the floor and a wooden shelf by the wall",
                "To the north is a closed door.", "To the south the hallway continues.", "To the west is an open door."};
    }

    @Override
    public RoomName goNorth() {
        return RoomName.CLOSED_PUSH_DOOR;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.HALL_MIDDLE;
    }

    @Override
    public RoomName goEast() {
        return RoomName.DEADEND;
    }

    @Override
    public RoomName goWest() {
        return RoomName.KITCHEN_NORTH;
    }
}
