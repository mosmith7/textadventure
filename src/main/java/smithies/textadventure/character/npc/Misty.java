package smithies.textadventure.character.npc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smithies.textadventure.character.BaseCharacter;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.item.Inventory;
import smithies.textadventure.map.DungeonMap;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.rooms.RoomName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class Misty extends BaseCharacter implements Npc {

    private static final Logger LOG = LoggerFactory.getLogger(Misty.class);

    private DungeonMap map;

    public Misty(DungeonMap map, Room currentRoom) {
        this.map = map;
        this.currentRoom = currentRoom;
        this.inventory = new Inventory(1);
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

        moveToRandomRoom();

        if (currentRoom.hasItem() && !isInventoryFull()) {
            currentRoom.takeItem(this, currentRoom.peekItem()).ifPresent(item -> {
                inventory.addItem(item);
                LOG.debug("Misty has picked up a: " + item.getName());
            });
        }
    }

    private void moveToRandomRoom() {
        Room room = chooseRandomValidRoom();
        this.currentRoom = room;
        LOG.debug("Misty is in: " + room.getName());
    }

    private Room chooseRandomValidRoom() {

        List<Adverb> validDirections = Directions.ALL_DIRECTIONS
                .stream()
                .filter(d ->  currentRoom.goDirection(d).isSuccessful())
                .collect(Collectors.toList());

        List<Room> allowedRooms = new ArrayList<>();
        for (Adverb direction : validDirections) {
            Optional<RoomName> roomName = getRoomInDirection(direction);
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
