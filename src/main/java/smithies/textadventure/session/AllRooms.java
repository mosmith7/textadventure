package smithies.textadventure.session;

import smithies.textadventure.rooms.Hall;
import smithies.textadventure.rooms.Kitchen;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages and remembers the current state for all of the rooms
 */
public class AllRooms {

    private List<Room> rooms = new ArrayList<>();

    public AllRooms() {
        rooms.add(new Kitchen());
        rooms.add(new Hall());
    }

    public Room get(RoomName name) {
        return rooms.stream().filter(r -> name.equals(r.getName())).findFirst().get();
    }
}
