package smithies.textadventure.session;

import smithies.textadventure.rooms.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages and remembers the current state for all of the rooms
 */
public class AllRooms {

    private List<Room> rooms = new ArrayList<>();

    public AllRooms() {
        rooms.add(new KitchenSouth());
        rooms.add(new KitchenNorth());
        rooms.add(new HallSouth());
        rooms.add(new HallMiddle());
        rooms.add(new HallNorth());
        rooms.add(new LivingRoom());
    }

    public Room get(RoomName name) {
        return rooms.stream().filter(r -> name.equals(r.getName())).findFirst().get();
    }
}
