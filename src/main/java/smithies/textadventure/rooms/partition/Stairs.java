package smithies.textadventure.rooms.partition;

public class Stairs implements RoomPartition {

    public Stairs() {
    }

    @Override
    public boolean isOpen() {
        return true;
    }
}
