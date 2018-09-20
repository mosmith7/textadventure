package smithies.textadventure.rooms.door;

public class ClosedDoor implements RoomPartition {

    private boolean pushable;

    public ClosedDoor(boolean pushable) {
        this.pushable = pushable;
    }

    @Override
    public boolean isDoor() {
        return true;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return pushable;
    }

}