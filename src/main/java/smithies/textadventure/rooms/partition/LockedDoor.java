package smithies.textadventure.rooms.partition;

public class LockedDoor implements Door {
    
    private boolean pushable;

    public LockedDoor(boolean pushable) {
        this.pushable = pushable;
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