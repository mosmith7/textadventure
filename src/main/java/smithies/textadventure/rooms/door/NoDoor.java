package smithies.textadventure.rooms.door;

public class NoDoor implements RoomPartition {

    public NoDoor() {
    }

    @Override
    public boolean isDoor() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

}
