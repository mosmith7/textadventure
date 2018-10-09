package smithies.textadventure.rooms.partition;

public interface Door extends RoomPartition {

    boolean isLocked();

    boolean isPushable();
}
