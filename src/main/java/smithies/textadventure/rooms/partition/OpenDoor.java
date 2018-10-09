package smithies.textadventure.rooms.partition;

public class OpenDoor implements Door {

    private boolean pushable;

    public OpenDoor(boolean pushable) {
        this.pushable = pushable;
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
        return pushable;
    }

}
