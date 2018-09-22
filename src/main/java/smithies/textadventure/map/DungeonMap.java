package smithies.textadventure.map;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.*;
import smithies.textadventure.rooms.door.*;

import java.util.ArrayList;
import java.util.List;

public class DungeonMap {

    private List<Room> rooms = new ArrayList<>();

    public DungeonMap() {

        Room hallSouth = new RoomBuilder(RoomName.HALL_SOUTH)
                .addNorthRoom(RoomName.HALL_MIDDLE, new NoDoor())
                .addEastRoom(RoomName.STUDY, new ClosedDoor(true))
                .addSouthRoom(RoomName.FRONT_GARDEN, new LockedDoor(false))
                .addWestRoom(RoomName.KITCHEN_SOUTH, new OpenDoor(false))
                .build();
        rooms.add(hallSouth);

        Room hallMiddle = new RoomBuilder(RoomName.HALL_MIDDLE)
                .addNorthRoom(RoomName.HALL_NORTH, new NoDoor())
                .addEastRoom(RoomName.LIVING_ROOM, new OpenDoor(true))
                .addSouthRoom(RoomName.HALL_SOUTH, new NoDoor())
                .build();
        rooms.add(hallMiddle);

        Room hallNorth = new RoomBuilder(RoomName.HALL_NORTH)
                .addNorthRoom(RoomName.TOILET, new ClosedDoor(true))
                .addSouthRoom(RoomName.HALL_MIDDLE, new NoDoor())
                .addWestRoom(RoomName.KITCHEN_NORTH, new OpenDoor(false))
                .build();
        rooms.add(hallNorth);

        Room kitchenNorth = new RoomBuilder(RoomName.KITCHEN_NORTH)
                .addEastRoom(RoomName.HALL_NORTH, new OpenDoor(true))
                .addSouthRoom(RoomName.KITCHEN_SOUTH, new NoDoor())
                .addWestRoom(RoomName.BACK_GARDEN, new LockedDoor(false))
                .build();
        rooms.add(kitchenNorth);

        Room kitchenSouth = new RoomBuilder(RoomName.KITCHEN_SOUTH)
                .addNorthRoom(RoomName.KITCHEN_NORTH, new NoDoor())
                .addEastRoom(RoomName.HALL_SOUTH, new OpenDoor(true))
                .build();
        rooms.add(kitchenSouth);

        Room livingRoom = new RoomBuilder(RoomName.LIVING_ROOM)
                .addSouthRoom(RoomName.BACK_GARDEN, new LockedDoor(false))
                .addWestRoom(RoomName.HALL_MIDDLE, new OpenDoor(true))
                .build();
        rooms.add(livingRoom);
    }

    public Room get(RoomName name) {
        return rooms.stream().filter(r -> name.equals(r.getName())).findFirst().get();
    }
}
