package smithies.textadventure.rooms;

public class KitchenSouth extends Room {

    private RoomName name = RoomName.KITCHEN_SOUTH;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public String[] getFullDescriptionLines() {
        return new String[]{"You are in the kitchen.", "To the north the kitchen continues.",
                "To the east is an open door"};
    }

    @Override
    public RoomName goNorth() {
        return RoomName.KITCHEN_NORTH;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.DEADEND;
    }

    @Override
    public RoomName goEast() {
        return RoomName.HALL_SOUTH;
    }

    @Override
    public RoomName goWest() {
        return RoomName.DEADEND;
    }
}
