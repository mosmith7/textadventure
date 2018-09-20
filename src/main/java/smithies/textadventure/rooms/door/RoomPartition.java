package smithies.textadventure.rooms.door;

public interface RoomPartition {

    boolean isDoor();

    boolean isOpen();

    boolean isLocked();

    boolean isPushable();
}
