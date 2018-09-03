package smithies.textadventure.rooms;

public class LivingRoom extends Room {

    private RoomName name = RoomName.LIVING_ROOM;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public String[] getFullDescriptionLines() {
        return new String[]{"You are in the living room.", "There is a christmas tree in the corner",
                "To the west, is an open door.", "To the north is a closed door."};
    }

    @Override
    public RoomName goNorth() {
        return RoomName.LOCKED_DOOR;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.DEADEND;
    }

    @Override
    public RoomName goEast() {
        return RoomName.DEADEND;
    }

    @Override
    public RoomName goWest() {
        return RoomName.HALL_MIDDLE;
    }
}