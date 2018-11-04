package smithies.textadventure.character.npc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.move.MoveState;
import smithies.textadventure.character.npc.move.MoveToCharacter;
import smithies.textadventure.character.npc.move.MoveToRoom;
import smithies.textadventure.character.npc.move.StationaryState;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

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
    private boolean sitting = false;

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
    public String getNameForSasha() {
        return "Your favourite biped";
    }

    @Override
    public String[] getDescriptionWhenInSameRoom() {
        List<String> messages = new ArrayList<>();
        String state = sitting ? "sitting" : "standing";
        messages.add(getNameForSasha() + " is " + state + " in the room.");
        if (!inventory.isEmpty()) {
            messages.add("In it's hand is a " + inventory.peek().get().name());
        }
        return messages.toArray(new String[0]);
    }

    @Override
    public void takeTurn() {
        if (moveStateLockedForTurns == 0) {
            int currentTurnNumber = turnNumber.getAndIncrement();

            if (!awake) tryToWakeUp(currentTurnNumber);

            currentMoveState.move();

            if (currentMoveState instanceof MoveToRoom && ((MoveToRoom) currentMoveState).isInTargetRoom()) {
                this.currentMoveState = new StationaryState(this);
                LOG.debug("{} has changed state to {}", getName(), currentMoveState);
            }

            if (currentMoveState instanceof MoveToCharacter && ((MoveToCharacter) currentMoveState).isWithTargetNpc()) {
                int turnsToRemainInSameRoom = ((MoveToCharacter) currentMoveState).getTurnsToRemainInSameRoom();
                this.currentMoveState = new StationaryState(this);
                this.moveStateLockedForTurns = turnsToRemainInSameRoom;
                LOG.debug("{} has changed state to {}", getName(), currentMoveState);
            }

            if (currentTurnNumber < 20 && RoomName.LIVING_ROOM.equals(currentRoom.getName())) {
                List<RoomName> dontGoVia = new ArrayList<>();
                dontGoVia.add(RoomName.STAIRS_SOUTH);
                dontGoVia.add(RoomName.FRONT_GARDEN);
                dontGoVia.add(RoomName.BACK_GARDEN);
                Npc misty = otherNpcs.stream().filter(npc -> "Misty".equals(npc.getName())).findFirst().get();
                currentMoveState = new MoveToCharacter(this, map, misty, dontGoVia);
            }

            changeStationaryState();
        } else {
            currentMoveState.move();
            moveStateLockedForTurns--;
        }

    }

    private void changeStationaryState() {
        boolean stationary = currentMoveState instanceof StationaryState;
        boolean roomWithChairs = currentRoom.getName().equals(RoomName.LIVING_ROOM)
                ||  currentRoom.getName().equals(RoomName.KITCHEN_SOUTH);
        if (stationary && roomWithChairs) {
            this.sitting = RANDOM.nextInt(10) > 3;
        }
    }

    private void tryToWakeUp(int currentTurnNumber) {
        if (currentTurnNumber > 5 && RANDOM.nextInt(10) > 7) {
            List<RoomName> routeExclusions = new ArrayList<>();
            routeExclusions.add(RoomName.BACK_GARDEN);
            routeExclusions.add(RoomName.FRONT_GARDEN);
            this.currentMoveState = new MoveToRoom(this, map, RoomName.LIVING_ROOM, routeExclusions);
            LOG.debug("{} has woken up!", getName());
            this.awake = true;
        }
    }
}
