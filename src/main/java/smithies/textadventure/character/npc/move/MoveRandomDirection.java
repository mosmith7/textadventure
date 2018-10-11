package smithies.textadventure.character.npc.move;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.Misty;
import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.*;
import java.util.stream.Collectors;

public class MoveRandomDirection implements MoveState {

    private static final Logger LOG = LoggerFactory.getLogger(MoveRandomDirection.class);

    private Npc npc;
    private DungeonMap map;

    public MoveRandomDirection(Npc npc, DungeonMap map) {
        this.npc = npc;
        this.map = map;
    }

    @Override
    public void move() {
        moveToRandomRoom();
    }

    private void moveToRandomRoom() {
        Room room = chooseRandomValidRoom();
        npc.setCurrentRoom(room);
        LOG.debug("{} is in: {}", npc.getName(), room.getName());
    }

    private Room chooseRandomValidRoom() {

        List<Adverb> validDirections = Directions.ALL_DIRECTIONS
                .stream()
                .filter(d ->  npc.getCurrentRoom().goDirection(d).isSuccessful())
                .collect(Collectors.toList());

        List<Room> allowedRooms = new ArrayList<>();
        for (Adverb direction : validDirections) {
            Optional<RoomName> roomName = npc.getRoomInDirection(direction);
            if (roomName.isPresent()) {
                Room room = map.get(roomName.get());
                if (room.isAllowedRoom()) allowedRooms.add(room);
            }
        }

        int options = allowedRooms.size();
        int option = new Random().nextInt(options);
        return allowedRooms.get(option);
    }
}
