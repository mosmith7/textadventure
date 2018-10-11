package smithies.textadventure.character.npc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.BaseCharacter;
import smithies.textadventure.character.npc.move.MoveRandomDirection;
import smithies.textadventure.character.npc.move.MoveState;
import smithies.textadventure.character.npc.move.StationaryState;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.*;
import java.util.stream.Collectors;

public class Misty extends BaseCharacter implements Npc {

    private static final Logger LOG = LoggerFactory.getLogger(Misty.class);

    private DungeonMap map;
    private MoveState currentMoveState;

    public Misty(DungeonMap map, Room currentRoom) {
        this.map = map;
        this.currentRoom = currentRoom;
        this.inventory = new Inventory(1);
        this.currentMoveState = new MoveRandomDirection(this, map);
    }

    @Override
    public String getName() {
        return "Misty";
    }

    @Override
    public String[] getDescriptionWhenInSameRoom() {
        List<String> messages = new ArrayList<>();
        messages.add("A fluffy black quadruped is standing in the room.");
        messages.add("It looks like it wants to play.");
        if (!inventory.isEmpty()) {
            messages.add("In it's mouth is a " + inventory.peek().get().name());
        }
        return messages.toArray(new String[0]);
    }

    @Override
    public void takeTurn() {
        // Misty should try to migrate to where the most humans are
        // But if she is in a room with a toy, she will try to take it
        // She should also have a tendancy to circle around the house

        randomlyChangeMoveState();
        currentMoveState.move();

        if (currentRoom.hasItem() && !isInventoryFull()) {
            currentRoom.takeItem(this, currentRoom.peekItem()).ifPresent(item -> {
                inventory.addItem(item);
                LOG.debug("{} has picked up a: {}", getName(), item.getName());
            });
        }
    }

    private void randomlyChangeMoveState() {
        List<MoveState> validMoveStates = new ArrayList<>();
        validMoveStates.add(new StationaryState(this));
        validMoveStates.add(new MoveRandomDirection(this, map));

        int options = validMoveStates.size();
        int option = new Random().nextInt(options);

        MoveState newMoveState = validMoveStates.get(option);
        if (newMoveState.getClass() != this.currentMoveState.getClass()) {
            this.currentMoveState = newMoveState;
            LOG.debug("{} has changed move state to: {}", getName(), newMoveState);
        }
    }
}
