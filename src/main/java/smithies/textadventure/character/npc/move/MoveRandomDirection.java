package smithies.textadventure.character.npc.move;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.GoDirectionFailure;
import smithies.textadventure.rooms.GoDirectionResponse;
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
        MoveRoomDetails moveRoomDetails = chooseRandomValidRoom();

        if (npc.canOpenDoors()) {
            Adverb direction = moveRoomDetails.getDirectionToRoom();
            npc.openDoor(direction);
            LOG.debug("{} opened door in direction", npc.getName(), direction);
        }

        Room room = moveRoomDetails.getRoom();
        npc.setCurrentRoom(room);
        LOG.debug("{} is in: {}", npc.getName(), npc.getCurrentRoom().getName());
    }

    private MoveRoomDetails chooseRandomValidRoom() {

        List<Adverb> validDirections = Directions.ALL_DIRECTIONS
                .stream()
                .filter(d ->  {
                    GoDirectionResponse goDirectionResponse = npc.getCurrentRoom().goDirection(d);
                    if (!goDirectionResponse.isSuccessful()) {
                        GoDirectionFailure response = (GoDirectionFailure) goDirectionResponse;
                        boolean closedDoor = GoDirectionResponse.CLOSED_DOOR.equals(response.getFailureReason());
                        if (closedDoor && npc.canOpenDoors()) return true;
                    }
                    return goDirectionResponse.isSuccessful();
                })
                .collect(Collectors.toList());

        List<MoveRoomDetails> allowedRooms = new ArrayList<>();
        for (Adverb direction : validDirections) {
            RoomName roomName = npc.getRoomInDirection(direction);
            if (!RoomName.DEADEND.equals(roomName)) {
                Room room = map.get(roomName);
                if (npc.isAllowedEverywhere() || room.isAllowedRoom()) {
                    allowedRooms.add(new MoveRoomDetails(room, direction));
                }
            }
        }

        int options = allowedRooms.size();
        int option = new Random().nextInt(options);
        return allowedRooms.get(option);
    }

    class MoveRoomDetails {
        private Room room;
        private Adverb directionToRoom;

        public MoveRoomDetails(Room room, Adverb directionToRoom) {
            this.room = room;
            this.directionToRoom = directionToRoom;
        }

        public Room getRoom() {
            return room;
        }

        public Adverb getDirectionToRoom() {
            return directionToRoom;
        }
    }
}
