package smithies.textadventure.map;

import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomBuilder;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.rooms.partition.*;

import java.util.ArrayList;
import java.util.List;

public class DungeonMap {

    private List<Room> rooms = new ArrayList<>();

    public DungeonMap() {

        buildDownstairsRooms();

        buildStairs();

        buildUpstairsRooms();

    }

    private void buildStairs() {
        Room stairsSouth = new RoomBuilder(RoomName.STAIRS_SOUTH, true)
                .addNorthRoom(RoomName.STAIRS_NORTH, new Stairs())
                .addSouthRoom(RoomName.HALL_SOUTH, new NoDoor())
                .build();
        rooms.add(stairsSouth);

        Room stairsNorth = new RoomBuilder(RoomName.STAIRS_NORTH, true)
                .addNorthRoom(RoomName.UPSTAIRS_LANDING_WEST, new NoDoor())
                .addSouthRoom(RoomName.STAIRS_SOUTH, new Stairs())
                .build();
        rooms.add(stairsNorth);
    }

    private void buildDownstairsRooms() {
        Room hallSouth = new RoomBuilder(RoomName.HALL_SOUTH, false)
                .addNorthEastRoom(RoomName.HALL_MIDDLE, new NoDoor())
                .addNorthRoom(RoomName.STAIRS_SOUTH, new Stairs())
                .addEastRoom(RoomName.STUDY, new ClosedDoor(true))
                .addSouthRoom(RoomName.FRONT_GARDEN, new LockedDoor(false))
                .addWestRoom(RoomName.KITCHEN_SOUTH, new OpenDoor(false))
                .build();
        rooms.add(hallSouth);

        Room hallMiddle = new RoomBuilder(RoomName.HALL_MIDDLE, false)
                .addNorthRoom(RoomName.HALL_NORTH, new NoDoor())
                .addEastRoom(RoomName.LIVING_ROOM, new OpenDoor(true))
                .addSouthRoom(RoomName.HALL_SOUTH, new NoDoor())
                .build();
        rooms.add(hallMiddle);

        Room hallNorth = new RoomBuilder(RoomName.HALL_NORTH, false)
                .addNorthRoom(RoomName.TOILET, new ClosedDoor(true))
                .addSouthRoom(RoomName.HALL_MIDDLE, new NoDoor())
                .addWestRoom(RoomName.KITCHEN_NORTH, new OpenDoor(false))
                .build();
        rooms.add(hallNorth);

        Room kitchenNorth = new RoomBuilder(RoomName.KITCHEN_NORTH, false)
                .addEastRoom(RoomName.HALL_NORTH, new OpenDoor(true))
                .addSouthRoom(RoomName.KITCHEN_SOUTH, new NoDoor())
                .addWestRoom(RoomName.BACK_GARDEN, new LockedDoor(false))
                .build();
        rooms.add(kitchenNorth);

        Room kitchenSouth = new RoomBuilder(RoomName.KITCHEN_SOUTH, false)
                .addNorthRoom(RoomName.KITCHEN_NORTH, new NoDoor())
                .addEastRoom(RoomName.HALL_SOUTH, new OpenDoor(true))
                .build();
        rooms.add(kitchenSouth);

        Room livingRoom = new RoomBuilder(RoomName.LIVING_ROOM, false)
                .addSouthRoom(RoomName.BACK_GARDEN, new LockedDoor(false))
                .addWestRoom(RoomName.HALL_MIDDLE, new OpenDoor(true))
                .build();
        rooms.add(livingRoom);
    }

    private void buildUpstairsRooms() {
        Room landingWest = new RoomBuilder(RoomName.UPSTAIRS_LANDING_WEST, true)
                .addEastRoom(RoomName.UPSTAIRS_LANDING_EAST, new NoDoor())
                .addSouthRoom(RoomName.STAIRS_NORTH, new Stairs())
                .addSouthWest(RoomName.BEDROOM_ONE, new ClosedDoor(true))
                .addWestRoom(RoomName.BEDROOM_TWO, new ClosedDoor(true))
                .build();
        rooms.add(landingWest);

        Room bedroomOne = new RoomBuilder(RoomName.BEDROOM_ONE, true)
                .addNorthRoom(RoomName.UPSTAIRS_LANDING_WEST, new ClosedDoor(false))
                .build();
        rooms.add(bedroomOne);

        Room bedroomTwo = new RoomBuilder(RoomName.BEDROOM_TWO, true)
                .addEastRoom(RoomName.UPSTAIRS_LANDING_WEST, new ClosedDoor(false))
                .build();
        rooms.add(bedroomTwo);

        Room landingEast = new RoomBuilder(RoomName.UPSTAIRS_LANDING_EAST, true)
                .addNorthRoom(RoomName.BATHROOM, new ClosedDoor(true))
                .addEastRoom(RoomName.BEDROOM_THREE, new ClosedDoor(true))
                .addSouthRoom(RoomName.BEDROOM_FOUR, new ClosedDoor(true))
                .addWestRoom(RoomName.UPSTAIRS_LANDING_WEST, new NoDoor())
                .build();
        rooms.add(landingEast);

        Room bedroomThree = new RoomBuilder(RoomName.BEDROOM_THREE, true)
                .addWestRoom(RoomName.UPSTAIRS_LANDING_EAST, new ClosedDoor(false))
                .build();
        rooms.add(bedroomThree);

        Room bedroomFour = new RoomBuilder(RoomName.BEDROOM_FOUR, true)
                .addNorthRoom(RoomName.UPSTAIRS_LANDING_EAST, new ClosedDoor(false))
                .build();
        rooms.add(bedroomFour);

        Room bathroom = new RoomBuilder(RoomName.BATHROOM, true)
                .addSouthRoom(RoomName.UPSTAIRS_LANDING_EAST, new ClosedDoor(false))
                .build();
        rooms.add(bathroom);
    }

    public Room get(RoomName name) {
        return rooms.stream().filter(r -> name.equals(r.getName())).findFirst().get();
    }
}
