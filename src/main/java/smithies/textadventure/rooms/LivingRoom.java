package smithies.textadventure.rooms;

public class LivingRoom extends Room {

    private RoomName name = RoomName.LIVING_ROOM;

    @Override
    public RoomName getName() {
        return name;
    }

}