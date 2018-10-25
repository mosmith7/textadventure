package smithies.textadventure.map;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapDirector {

    private DungeonMap map;

    public MapDirector(DungeonMap map) {
        this.map = map;
    }

    public List<Adverb> findDirectionRouteBetweenRooms(Room startingRoom, Room targetRoom) {
        List<RoomName> roomRoute = findRoomRouteBetweenRooms(startingRoom, targetRoom);
        List<Adverb> directionRoute = new ArrayList<>();
        for (int i = 1; i < roomRoute.size(); i++) {
            RoomName nextRoom = roomRoute.get(i);
            RoomName currentRoomName = roomRoute.get(i - 1);
            Room currentRoom = map.get(currentRoomName);
            directionRoute.add(currentRoom.getDirectionOfRoom(nextRoom).get());
        }
        return directionRoute;
    }

    public List<RoomName> findRoomRouteBetweenRooms(Room startingRoom, Room targetRoom) {
        // Start to trace out route, when you go back on yourself, remove step from route
        List<RoomName> route = new ArrayList<>();
        route.add(startingRoom.getName());
        Room currentRoom = startingRoom;
        while (!currentRoom.equals(targetRoom)) {
            route = continueRoute(currentRoom, route);
            currentRoom = map.get(route.get(route.size() - 1));
        }
        return route;
    }

    private List<RoomName> continueRoute(Room currentRoom, List<RoomName> route) {
        RoomName nextRoomName = getRandomNextRoomName(currentRoom);

        if (route.contains(nextRoomName)) {
            // If we have gone back on ourselves, remove all rooms up to that point
            route = route.subList(0, route.indexOf(nextRoomName) + 1);
        } else {
            route.add(nextRoomName);
        }

        return route;
    }

    private RoomName getRandomNextRoomName(Room currentRoom) {
        RoomName nextRoomName = RoomName.DEADEND;
        while (RoomName.DEADEND.equals(nextRoomName)) {
            Adverb randomDirection = Directions.getRandomDirection();
            nextRoomName = currentRoom.getRoom(randomDirection);
        }
        return nextRoomName;
    }
}
