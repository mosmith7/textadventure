package smithies.textadventure.rooms.door;

public class Deadend implements RoomPartition {

    public Deadend() {
    }

    @Override
    public boolean isDoor() {
        return false;
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
        return false;
    }

}