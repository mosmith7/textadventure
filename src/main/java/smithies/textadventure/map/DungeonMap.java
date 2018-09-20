package smithies.textadventure.map;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.*;
import smithies.textadventure.rooms.door.*;

import java.util.ArrayList;
import java.util.List;

public class DungeonMap {

    private List<Room> rooms = new ArrayList<>();

    public DungeonMap() {

        HallSouth hallSouth = new HallSouth();
        hallSouth.addRoom(Adverb.NORTH, RoomName.HALL_MIDDLE, new NoDoor());
        hallSouth.addRoom(Adverb.EAST, RoomName.STUDY, new ClosedDoor(true));
        hallSouth.addRoom(Adverb.SOUTH, RoomName.LOCKED_DOOR, new LockedDoor(false));
        hallSouth.addRoom(Adverb.WEST, RoomName.KITCHEN_SOUTH, new OpenDoor(false));
        rooms.add(hallSouth);

        HallMiddle hallMiddle = new HallMiddle();
        hallMiddle.addRoom(Adverb.NORTH, RoomName.HALL_NORTH, new NoDoor());
        hallMiddle.addRoom(Adverb.EAST, RoomName.LIVING_ROOM, new OpenDoor(true));
        hallMiddle.addRoom(Adverb.SOUTH, RoomName.HALL_SOUTH, new NoDoor());
        hallMiddle.addRoom(Adverb.WEST, RoomName.DEADEND, new Deadend());
        rooms.add(hallMiddle);

        HallNorth hallNorth = new HallNorth();
        hallNorth.addRoom(Adverb.NORTH, RoomName.TOILET, new ClosedDoor(true));
        hallNorth.addRoom(Adverb.EAST, RoomName.DEADEND, new Deadend());
        hallNorth.addRoom(Adverb.SOUTH, RoomName.HALL_MIDDLE, new NoDoor());
        hallNorth.addRoom(Adverb.WEST, RoomName.KITCHEN_NORTH, new OpenDoor(false));
        rooms.add(hallNorth);

        KitchenNorth kitchenNorth = new KitchenNorth();
        kitchenNorth.addRoom(Adverb.NORTH, RoomName.DEADEND, new Deadend());
        kitchenNorth.addRoom(Adverb.EAST, RoomName.HALL_NORTH, new OpenDoor(true));
        kitchenNorth.addRoom(Adverb.SOUTH, RoomName.KITCHEN_SOUTH, new NoDoor());
        kitchenNorth.addRoom(Adverb.WEST, RoomName.DEADEND, new LockedDoor(false));
        rooms.add(kitchenNorth);

        KitchenSouth kitchenSouth = new KitchenSouth();
        kitchenSouth.addRoom(Adverb.NORTH, RoomName.KITCHEN_NORTH, new NoDoor());
        kitchenSouth.addRoom(Adverb.EAST, RoomName.HALL_SOUTH, new OpenDoor(true));
        kitchenSouth.addRoom(Adverb.SOUTH, RoomName.DEADEND, new Deadend());
        kitchenSouth.addRoom(Adverb.WEST, RoomName.DEADEND, new Deadend());
        rooms.add(kitchenSouth);

        LivingRoom livingRoom = new LivingRoom();
        livingRoom.addRoom(Adverb.NORTH, RoomName.DEADEND, new Deadend());
        livingRoom.addRoom(Adverb.EAST, RoomName.DEADEND, new Deadend());
        livingRoom.addRoom(Adverb.SOUTH, RoomName.LOCKED_DOOR, new LockedDoor(false));
        livingRoom.addRoom(Adverb.WEST, RoomName.HALL_MIDDLE, new OpenDoor(true));
        rooms.add(livingRoom);
    }

    public Room get(RoomName name) {
        return rooms.stream().filter(r -> name.equals(r.getName())).findFirst().get();
    }
}
