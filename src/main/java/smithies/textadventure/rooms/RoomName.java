package smithies.textadventure.rooms;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum RoomName {

    DEADEND(false),
    CLOSED_PUSH_DOOR(false),
    CLOSED_PULL_DOOR(false),
    LOCKED_DOOR(false),

    HALL_SOUTH(true),
    HALL_MIDDLE(true),
    HALL_NORTH(true),
    KITCHEN_SOUTH(true),
    KITCHEN_NORTH(true),
    LIVING_ROOM(true),
    STUDY(true);

    private boolean validRoom;

    RoomName(boolean validRoom) {
        this.validRoom = validRoom;
    }

    public boolean isValidRoom() {
        return validRoom;
    }
}
