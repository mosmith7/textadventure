package smithies.textadventure.character.npc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.npc.move.MoveState;
import smithies.textadventure.character.npc.move.MoveToCharacter;
import smithies.textadventure.character.npc.move.MoveToRoom;
import smithies.textadventure.character.npc.move.StationaryState;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.state.Scratch;
import smithies.textadventure.command.state.ScratchDoor;
import smithies.textadventure.command.state.Whine;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;
import smithies.textadventure.rooms.partition.Door;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HumanCharacter extends BaseNpcCharacter {

    private static final Logger LOG = LoggerFactory.getLogger(HumanCharacter.class);
    public static final Random RANDOM = new Random();
    public static final int RATING_MAX = 10;

    private MoveState currentMoveState;
    private AtomicInteger turnNumber = new AtomicInteger(0);
    private boolean awake = false;
    private boolean sitting = false;
    private int sashaFondnessRating;
    private int wakeUpRating;
    private String name;
    private String nameForSasha;

    public HumanCharacter(String name, String nameForSasha, DungeonMap map, Room currentRoom, int sashaFondnessRating, int wakeUpRating) {
        super(map);
        this.name = name;
        this.nameForSasha = nameForSasha;
        this.currentRoom = currentRoom;
        this.inventory = new Inventory(5);
        this.currentMoveState = new StationaryState(this);
        this.sashaFondnessRating = sashaFondnessRating;
        this.wakeUpRating = wakeUpRating;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameForSasha() {
        return nameForSasha;
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

            boolean interactWithSasha = interactWithSashaCheck();
            if (interactWithSasha) {
                if (player.getCurrentState() instanceof Whine) {
                    findAndThrowToy();
                } else if (player.getCurrentState() instanceof ScratchDoor) {
                    openDoorForSasha();
                } else {
                    output.displayTextLine(String.format("%s is stroking you", this.getNameForSasha()));
                }
            } else {
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
                    List<RoomName> avoidRooms = new ArrayList<>();
                    avoidRooms.add(RoomName.STAIRS_SOUTH);
                    avoidRooms.add(RoomName.FRONT_GARDEN);
                    avoidRooms.add(RoomName.BACK_GARDEN);
                    Npc misty = otherNpcs.stream().filter(npc -> "Misty".equals(npc.getName())).findFirst().get();
                    currentMoveState = new MoveToCharacter(this, map, misty, avoidRooms);
                }

                changeStationaryState();
            }
        } else {
            currentMoveState.move();
            moveStateLockedForTurns--;
        }

    }

    private boolean interactWithSashaCheck() {
        return currentRoom.equals(player.getCurrentRoom()) && ((ratingCheck(sashaFondnessRating)) ||
                (player.getCurrentState() instanceof Whine && sashaFondnessRating > RATING_MAX / 2) ||
                (player.getCurrentState() instanceof ScratchDoor && sashaFondnessRating > RATING_MAX / 2));
    }

    private void findAndThrowToy() {
        currentRoom.takeItem(this).ifPresent(i -> {
            ArrayList<RoomName> excludeRooms = new ArrayList<>();
            excludeRooms.add(RoomName.FRONT_GARDEN);
            Room adjacentRoom = map.getMapDirector().getRandomAdjacentRoom(currentRoom, excludeRooms, true);
            Adverb directionToRoom = currentRoom.getDirectionOfRoom(adjacentRoom.getName()).get();
            currentRoom.openDoor(directionToRoom);
            adjacentRoom.addItemToFloor(i);
            output.displayTextLine(String.format("%s throws the %s into the %s",
                    getNameForSasha(), i.getName(), adjacentRoom.getName()));
        });
    }

    private void openDoorForSasha() {
        ScratchDoor state = (ScratchDoor) player.getCurrentState();
        Adverb direction = state.getDirection();
        Door door = state.getDoor();
        if (!door.isLocked()) {
            if (!door.isOpen()) {
                currentRoom.openDoor(direction);
                output.displayTextLine(String.format("%s opens the %s door for you",
                        getNameForSasha(), direction));
            }
        }
    }

    private void changeStationaryState() {
        boolean stationary = currentMoveState instanceof StationaryState;
        boolean roomWithChairs = currentRoom.getName().equals(RoomName.LIVING_ROOM)
                ||  currentRoom.getName().equals(RoomName.KITCHEN_SOUTH);
        if (stationary && roomWithChairs) {
            this.sitting = ratingCheck(3);
        }
    }

    private void tryToWakeUp(int currentTurnNumber) {
        if (currentTurnNumber > 5 && ratingCheck(wakeUpRating)) {
            List<RoomName> routeExclusions = new ArrayList<>();
            routeExclusions.add(RoomName.BACK_GARDEN);
            routeExclusions.add(RoomName.FRONT_GARDEN);
            this.currentMoveState = new MoveToRoom(this, map, RoomName.LIVING_ROOM, routeExclusions);
            LOG.debug("{} has woken up!", getName());
            this.awake = true;
        }
    }

    private boolean ratingCheck(int rating) {
        return RANDOM.nextInt(RATING_MAX) < rating;
    }
}
