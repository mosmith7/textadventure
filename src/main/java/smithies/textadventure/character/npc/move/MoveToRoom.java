package smithies.textadventure.character.npc.move;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.map.MapDirector;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.rooms.partition.ClosedDoor;
import smithies.textadventure.rooms.partition.RoomPartition;

import java.util.ArrayList;
import java.util.List;

public class MoveToRoom implements MoveState {

    private static final Logger LOG = LoggerFactory.getLogger(MoveToRoom.class);

    private Npc npc;
    private DungeonMap map;
    private RoomName targetRoom;
    private List<Adverb> route;
    private MapDirector mapDirector;

    public MoveToRoom(Npc npc, DungeonMap map, RoomName targetRoom, List<RoomName> routeExclusions) {
        this.npc = npc;
        this.map = map;
        this.targetRoom = targetRoom;
        this.mapDirector = new MapDirector(map);
        this.route = mapDirector.findDirectionRouteBetweenRooms(npc.getCurrentRoom(), map.getRoomByName(targetRoom), routeExclusions);
    }

    public MoveToRoom(Npc npc, DungeonMap map, RoomName targetRoom) {
        this(npc, map, targetRoom, new ArrayList<>());
    }

    @Override
    public void move() {
        // Get partition, if closed door, open it.
        Adverb direction = route.get(0);
        RoomPartition partition = npc.getPartitionInDirection(direction);
        RoomName roomName = npc.getRoomInDirection(direction);
        Room targetRoom = map.getRoomByName(roomName);
        if (partition instanceof ClosedDoor && npc.canOpenDoors()) {
            map.openDoor(npc.getCurrentRoom(), targetRoom);
            LOG.debug("{} opened door in direction", npc.getName(), direction);
        }

        // Move to new room
        npc.setCurrentRoom(targetRoom);

        // Remove step of route from route list
        route.remove(0);

        LOG.debug("{} is in: {}", npc.getName(), npc.getCurrentRoom().getName());
    }

    public boolean isInTargetRoom() {
        return npc.getCurrentRoom().getName().equals(targetRoom);
    }
}
