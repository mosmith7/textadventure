package smithies.textadventure.rooms.partition;

public class NoDoor implements RoomPartition {

    public NoDoor() {
    }

    @Override
    public boolean isOpen() {
        return true;
    }

}
