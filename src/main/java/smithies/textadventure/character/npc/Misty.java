package smithies.textadventure.character.npc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.BaseCharacter;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.session.AllRooms;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Misty extends BaseCharacter implements Npc {

    private static final Logger LOG = LoggerFactory.getLogger(Misty.class);

    private AllRooms allRooms;

    public Misty(AllRooms allRooms, Room currentRoom) {
        this.allRooms = allRooms;
        this.currentRoom = currentRoom;
        this.inventory = new Inventory(1);
    }

    @Override
    public String[] getDescriptionWhenInSameRoom() {
        return new String[]{
            "A fluffy black quadruped is standing in the room.",
            "It looks like it wants to play."
        };
    }

    @Override
    public void takeTurn() {
        // Misty should try to migrate to where the most humans are
        // But if she is in a room with a toy, she will try to take it
        // She should also have a tendancy to circle

        List<Adverb> directionOptions = Directions.ALL_DIRECTIONS.stream().filter(d -> {
            boolean canMoveInDirection = false;
            if (currentRoom.goDirection(d).isPresent()) {
                canMoveInDirection = currentRoom.goDirection(d).get().isValidRoom();
            }
            return canMoveInDirection;
        }).collect(Collectors.toList());

        if (directionOptions.size() > 0) {
            goDirection(chooseRandomDirection(directionOptions)).ifPresent(roomName -> {
                this.currentRoom = allRooms.get(roomName);
                LOG.debug("Misty is in: " + roomName);
            });
        }

        // TODO: If there is an unguarded toy in the same room take it.
    }

    private Adverb chooseRandomDirection(List<Adverb> directions) {
        int options = directions.size();
        int option = new Random().nextInt(options);
        return directions.get(option);
    }
}
