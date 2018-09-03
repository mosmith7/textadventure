package smithies.textadventure.rooms;

public class KitchenSouth extends Room {

    private RoomName name = RoomName.KITCHEN_SOUTH;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public void displayFullDescription() {
        displayName();
        output.displayTextLines("You are in the kitchen.", "To the ");
    }
    @Override
    public RoomName goNorth() {
        return RoomName.HALL_SOUTH;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.HALL_SOUTH;
    }

    @Override
    public RoomName goEast() {
        return RoomName.HALL_SOUTH;
    }

    @Override
    public RoomName goWest() {
        return RoomName.HALL_SOUTH;
    }
}
