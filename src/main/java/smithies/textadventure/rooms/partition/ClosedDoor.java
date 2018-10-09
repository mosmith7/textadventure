package smithies.textadventure.rooms.partition;

public class ClosedDoor implements Door {

    private boolean pushable;

    public ClosedDoor(boolean pushable) {
        this.pushable = pushable;
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