package smithies.textadventure.map;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.*;
import smithies.textadventure.rooms.door.ClosedDoor;
import smithies.textadventure.rooms.door.LockedDoor;
import smithies.textadventure.rooms.door.NoDoor;
import smithies.textadventure.rooms.door.OpenDoor;

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

        rooms.add(new HallMiddle());
        rooms.add(new HallNorth());
        rooms.add(new KitchenSouth());
        rooms.add(new KitchenNorth());
        rooms.add(new LivingRoom());
    }

    public Room get(RoomName name) {
        return rooms.stream().filter(r -> name.equals(r.getName())).findFirst().get();
    }
}
