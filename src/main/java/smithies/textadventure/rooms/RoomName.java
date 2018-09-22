package smithies.textadventure.rooms;

public enum RoomName {

    DEADEND(false),

    // Rooms downstairs, which dogs are allowed in
    HALL_SOUTH(true),
    HALL_MIDDLE(true),
    HALL_NORTH(true),
    KITCHEN_SOUTH(true),
    KITCHEN_NORTH(true),
    LIVING_ROOM(true),
    STUDY(true),
    TOILET(true),

    // Rooms upstairs, which dogs are not allowed in
    STAIRS_SOUTH(false),
    STAIRS_NORTH(false),
    UPSTAIRS_LANDING_WEST(false),
    UPSTAIRS_LANDING_EAST(false),
    BATHROOM(false),
    BEDROOM_ONE(false),
    BEDROOM_TWO(false),
    BEDROOM_THREE(false),
    BEDROOM_FOUR(false),

    // 'Rooms' outside
    BACK_GARDEN(false),
    FRONT_GARDEN(false),
    ;

    private boolean forbiddenRoom;

    RoomName(boolean forbiddenRoom) {
        this.forbiddenRoom = forbiddenRoom;
    }

    public boolean isForbiddenRoom() {
        return forbiddenRoom;
    }
}
