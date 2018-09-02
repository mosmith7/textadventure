package smithies.textadventure.rooms;

import smithies.textadventure.command.UserInputCommand;

public class Kitchen extends Room {

    private RoomName name = RoomName.KITCHEN;

    @Override
    public RoomName getName() {
        return name;
    }

    @Override
    public void enter() {
        System.out.println("Welcome to the Kitchen");
    }

    @Override
    public RoomName goNorth() {
        return RoomName.HALL;
    }

    @Override
    public RoomName goSouth() {
        return RoomName.HALL;
    }

    @Override
    public RoomName goEast() {
        return RoomName.HALL;
    }

    @Override
    public RoomName goWest() {
        return RoomName.HALL;
    }
}
