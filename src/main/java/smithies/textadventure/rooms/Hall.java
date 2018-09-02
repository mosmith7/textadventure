package smithies.textadventure.rooms;

public class Hall extends Room {

    private RoomName name = RoomName.HALL;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public void enter() {
        System.out.println("Welcome to the Hall");
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
