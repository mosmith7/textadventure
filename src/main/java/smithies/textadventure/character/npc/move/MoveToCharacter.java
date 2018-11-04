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

import java.util.List;

public class MoveToCharacter implements MoveState {

    private static final Logger LOG = LoggerFactory.getLogger(MoveToRoom.class);

    private int turnsToRemainInSameRoom = 3;

    private Npc npc;
    private Npc targetNpc;
    private DungeonMap map;
    private RoomName targetRoom;
    private List<Adverb> route;
    private MapDirector mapDirector;

    public MoveToCharacter(Npc npc, DungeonMap map, Npc targetNpc, List<RoomName> routeExclusions) {
        this.npc = npc;
        this.targetNpc = targetNpc;
        this.map = map;
        this.targetRoom = targetNpc.getCurrentRoom().getName();
        this.mapDirector = new MapDirector(map);
        this.route = mapDirector.findDirectionRouteBetweenRooms(npc.getCurrentRoom(), map.getRoomByName(targetRoom), routeExclusions);
        this.targetNpc.setCurrentMoveState(new StationaryState(targetNpc), route.size() + getTurnsToRemainInSameRoom());
    }

    @Override
    public void move() {
        if (!isWithTargetNpc()) {
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
    }

    public boolean isWithTargetNpc() {
        return npc.getCurrentRoom().getName().equals(targetNpc.getCurrentRoom().getName());
    }

    public int getTurnsToRemainInSameRoom() {
        return turnsToRemainInSameRoom;
    }
}