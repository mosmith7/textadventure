package smithies.textadventure.rooms;

public enum RoomName {

    DEADEND(false, false),
    CLOSED_PUSH_DOOR(false, false),
    CLOSED_DOOR(false, false),
    CLOSED_PULL_DOOR(false, false),
    LOCKED_DOOR(false, false),

    // Rooms downstairs, which dogs are allowed in
    HALL_SOUTH(true, true),
    HALL_MIDDLE(true, true),
    HALL_NORTH(true, true),
    KITCHEN_SOUTH(true, true),
    KITCHEN_NORTH(true, true),
    LIVING_ROOM(true, true),
    STUDY(true, true),
    TOILET(true, true),

    // Rooms upstairs, which dogs are not allowed in
    STAIRS_SOUTH(true, false),
    STAIRS_NORTH(true, false),
    UPSTAIRS_LANDING_WEST(true, false),
    UPSTAIRS_LANDING_EAST(true, false),
    BATHROOM(true, false),
    BEDROOM_ONE(true, false),
    BEDROOM_TWO(true, false),
    BEDROOM_THREE(true, false),
    BEDROOM_FOUR(true, false),
    ;

    private boolean validRoom;

    private boolean forbiddenRoom;

    RoomName(boolean validRoom, boolean forbiddenRoom) {
        this.validRoom = validRoom;
        this.forbiddenRoom = forbiddenRoom;
    }

    public boolean isValidRoom() {
        return validRoom;
    }

    public boolean isForbiddenRoom() {
        return forbiddenRoom;
    }
}
