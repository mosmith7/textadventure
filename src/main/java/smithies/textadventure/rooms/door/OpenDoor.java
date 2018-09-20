package smithies.textadventure.rooms.door;

public class OpenDoor implements RoomPartition {

    private boolean pushable;

    public OpenDoor(boolean pushable) {
        this.pushable = pushable;
    }

    @Override
    public boolean isDoor() {
        return true;
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
