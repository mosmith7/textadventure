package smithies.textadventure.rooms;

public class GoDirectionSuccess implements GoDirectionResponse {

    private boolean successful;
    private RoomName roomName;

    public GoDirectionSuccess(RoomName roomName) {
        this.successful = true;
        this.roomName = roomName;
    }

    @Override
    public boolean isSuccessful() {
        return successful;
    }

    public RoomName getRoomName() {
        return roomName;
    }
}
