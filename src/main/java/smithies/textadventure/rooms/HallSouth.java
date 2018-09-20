package smithies.textadventure.rooms;

public class HallSouth extends Room {

    private RoomName name = RoomName.HALL_SOUTH;

    public HallSouth() {

    }

    @Override
    public RoomName getName() {
        return name;
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
