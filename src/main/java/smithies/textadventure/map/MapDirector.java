package smithies.textadventure.map;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.ArrayList;
import java.util.List;

public class MapDirector {

    private DungeonMap map;

    public MapDirector(DungeonMap map) {
        this.map = map;
    }

    public List<Adverb> findDirectionRouteBetweenRooms(Room startingRoom, Room targetRoom, List<RoomName> routeExclusions) {
        List<RoomName> roomRoute = findRoomRouteBetweenRooms(startingRoom, targetRoom, routeExclusions);
        List<Adverb> directionRoute = new ArrayList<>();
        for (int i = 1; i < roomRoute.size(); i++) {
            RoomName nextRoom = roomRoute.get(i);
            RoomName currentRoomName = roomRoute.get(i - 1);
            Room currentRoom = map.get(currentRoomName);
            directionRoute.add(currentRoom.getDirectionOfRoom(nextRoom).get());
        }
        return directionRoute;
    }

    public List<RoomName> findRoomRouteBetweenRooms(Room startingRoom, Room targetRoom, List<RoomName> routeExclusions) {
        // Start to trace out route, when you go back on yourself, remove step from route
        List<RoomName> route = new ArrayList<>();
        route.add(startingRoom.getName());
        Room currentRoom = startingRoom;
        while (!currentRoom.equals(targetRoom)) {
            route = continueRoute(currentRoom, route, routeExclusions);
            currentRoom = map.get(route.get(route.size() - 1));
        }
        return route;
    }

    public Room getRandomAdjacentRoom(Room currentRoom) {
        return map.get(getRandomNextRoomName(currentRoom, new ArrayList<>()));
    }

    private List<RoomName> continueRoute(Room currentRoom, List<RoomName> route, List<RoomName> routeExclusions) {
        RoomName nextRoomName = getRandomNextRoomName(currentRoom, routeExclusions);

        if (route.contains(nextRoomName)) {
            // If we have gone back on ourselves, remove all rooms up to that point
            route = route.subList(0, route.indexOf(nextRoomName) + 1);
        } else {
            route.add(nextRoomName);
        }

        return route;
    }

    private RoomName getRandomNextRoomName(Room currentRoom, List<RoomName> routeExclusions) {
        RoomName nextRoomName = RoomName.DEADEND;
        if (!routeExclusions.contains(RoomName.DEADEND)) routeExclusions.add(RoomName.DEADEND);
        while (routeExclusions.contains(nextRoomName)) {
            Adverb randomDirection = Directions.getRandomDirection();
            nextRoomName = currentRoom.getRoom(randomDirection);
        }
        return nextRoomName;
    }
}
