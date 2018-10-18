package smithies.textadventure.character.npc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.move.MoveRandomDirection;
import smithies.textadventure.character.npc.move.MoveState;
import smithies.textadventure.character.npc.move.StationaryState;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Mark extends BaseNpcCharacter {

    private static final Logger LOG = LoggerFactory.getLogger(Mark.class);
    public static final Random RANDOM = new Random();

    private MoveState currentMoveState;
    private AtomicInteger turnNumber = new AtomicInteger(0);
    private boolean awake = false;

    public Mark(DungeonMap map, Room currentRoom) {
        super(map);
        this.currentRoom = currentRoom;
        this.inventory = new Inventory(5);
        this.currentMoveState = new StationaryState(this);
    }

    @Override
    public String getName() {
        return "Mark";
    }

    @Override
    public String[] getDescriptionWhenInSameRoom() {
        List<String> messages = new ArrayList<>();
        messages.add("Your favourite biped is standing in the room.");
        if (!inventory.isEmpty()) {
            messages.add("In it's hand is a " + inventory.peek().get().name());
        }
        return messages.toArray(new String[0]);
    }

    @Override
    public void takeTurn() {
        int currentTurnNumber = turnNumber.getAndIncrement();

        if (!awake) tryToWakeUp(currentTurnNumber);

        currentMoveState.move();
    }

    private void tryToWakeUp(int currentTurnNumber) {
        if (currentTurnNumber > 5 && RANDOM.nextInt(10) > 7) {
            this.currentMoveState = new MoveRandomDirection(this, map);
            LOG.debug("{} has woken up!", getName());
        }
    }
}
