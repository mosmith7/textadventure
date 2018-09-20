package smithies.textadventure.rooms.door;

public class LockedDoor implements RoomPartition {
    
    private boolean pushable;

    public LockedDoor(boolean pushable) {
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
        return true;
    }

    @Override
    public boolean isPushable() {
        return pushable;
    }

}