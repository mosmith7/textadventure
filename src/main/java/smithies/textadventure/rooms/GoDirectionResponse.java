package smithies.textadventure.rooms;

public interface GoDirectionResponse {

    String LOCKED_DOOR = "LOCKED_DOOR";
    String CLOSED_DOOR = "CLOSED_DOOR";
    String WALL = "WALL";

    boolean isSuccessful();
}
