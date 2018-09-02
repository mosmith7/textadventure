package smithies.textadventure.rooms;

public abstract class Room {

    public abstract RoomName getName();

    public abstract void enter();

    public abstract RoomName goNorth();

    public abstract RoomName goSouth();

    public abstract RoomName goEast();

    public abstract RoomName goWest();
}
