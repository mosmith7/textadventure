package smithies.textadventure.rooms;

public class KitchenNorth extends Room {

    private RoomName name = RoomName.KITCHEN_SOUTH;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public void displayFullDescription() {
        displayName();
        output.displayTextLines("You are in the kitchen.", "To the south the kitchen continues",
                "To the east there is an open door", "To the west there is a closed door");
    }
    @Override
    public RoomName goNorth() {
        return RoomName.DEADEND;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.KITCHEN_SOUTH;
    }

    @Override
    public RoomName goEast() {
        return RoomName.HALL_NORTH;
    }

    @Override
    public RoomName goWest() {
        return RoomName.LOCKED_DOOR;
    }
}