package smithies.textadventure.rooms.partition;

public class Deadend implements RoomPartition {

    public Deadend() {
    }

    @Override
    public boolean isOpen() {
        return false;
    }

}