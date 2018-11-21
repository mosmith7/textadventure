package smithies.textadventure.rooms;

import smithies.textadventure.character.GameCharacter;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Directions;
import smithies.textadventure.command.Noun;
import smithies.textadventure.interactable.searchable.Interactable;
import smithies.textadventure.interactable.searchable.Searchable;
import smithies.textadventure.item.Item;
import smithies.textadventure.rooms.partition.*;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.*;
import java.util.stream.Collectors;

public class Room {

    private DisplayOutput output = new DisplayConsoleOutput();

    private RoomName name;
    private boolean forbiddenRoom;

    private boolean firstEntrance = true;
    private List<Item> items = new ArrayList<>();
    private Map<Item, String> itemPositionDescription = new HashMap<>();
    private List<Interactable> searchables = new ArrayList<>();
    protected Map<Adverb, RoomPartition> partitionByDirection = new HashMap<>();
    protected Map<Adverb, RoomName> roomsByDirection = new HashMap<>();

    public Room(RoomName name, boolean forbiddenRoom) {
        this.name = name;
        this.forbiddenRoom = forbiddenRoom;
    }

    public RoomName getName() {
        return name;
    }

    public boolean isForbiddenRoom() {
        return forbiddenRoom;
    }

    public boolean isAllowedRoom() {
        return !isForbiddenRoom();
    }

    public void addRoom(Adverb direction, RoomName room, RoomPartition door) {
        roomsByDirection.put(direction, room);
        partitionByDirection.put(direction, door);
    }

    public RoomPartition getPartition(Adverb direction) {
        return partitionByDirection.getOrDefault(direction, new Deadend());
    }

    public void openDoor(Adverb direction) {
        RoomPartition partition = partitionByDirection.get(direction);
        if (partition instanceof ClosedDoor) {
            ClosedDoor door = (ClosedDoor) partition;
            partitionByDirection.put(direction, new OpenDoor(door.isPushable()));
        }
    }

    public RoomName getRoom(Adverb direction) {
        return roomsByDirection.get(direction);
    }

    public Optional<Adverb> getDirectionOfRoom(RoomName roomName) {
        return roomsByDirection.keySet().stream().filter(direction -> {
            return roomName.equals(roomsByDirection.get(direction));
        }).findFirst();
    }

    public String[] getFullDescriptionLines() {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < Directions.MAIN_COMPASS_DIRECTIONS.size(); i++) {
            Adverb direction = Directions.MAIN_COMPASS_DIRECTIONS.get(i);
            String prefix = "To the " + direction + " ";
            RoomPartition partition = getPartition(direction);
            if (partition instanceof Door) {
                Door door = (Door) partition;
                if (door.isOpen()) {
                    lines.add(prefix + "is an open door");
                } else if (door.isLocked()) {
                    lines.add(prefix + "is a locked door");
                } else {
                    lines.add(prefix + "is a closed door");
                }
            } else if (partition instanceof Stairs) {
                lines.add(prefix + "are stairs");
            } else if (partition.isOpen()) {
                lines.add(prefix + "the room continues");
            } else {
                lines.add(prefix + "is a wall");
            }
        }
        for (int i = 0; i < Directions.MINOR_COMPASS_DIRECTIONS.size(); i++) {
            Adverb direction = Directions.MINOR_COMPASS_DIRECTIONS.get(i);
            String prefix = "To the " + direction + " ";
            RoomPartition partition = getPartition(direction);
            if (partition instanceof Door) {
                Door door = (Door) partition;
                if (door.isOpen()) {
                    lines.add(prefix + "is an open door");
                } else if (door.isLocked()) {
                    lines.add(prefix + "is a locked door");
                } else {
                    lines.add(prefix + "is a closed door");
                }
            } else if (partition instanceof Stairs) {
                lines.add(prefix + "are stairs");
            } else if (partition.isOpen()) {
                lines.add(prefix + "the room continues");
            } else {
                // Don't bother listing
            }
        }

        return lines.toArray(new String[lines.size()]);
    }

    public GoDirectionResponse goDirection(Adverb direction) {
        if (Directions.ALL_DIRECTIONS.contains(direction)) {
            RoomPartition roomPartition = getPartition(direction);
            if (roomPartition.isOpen()) {
                return new GoDirectionSuccess(getRoom(direction));
            } else if (roomPartition instanceof Door) {
                Door door = (Door) roomPartition;
                if (roomPartition.isOpen()) {
                    return new GoDirectionSuccess(getRoom(direction));
                } else if (door.isLocked()) {
                    return new GoDirectionFailure(GoDirectionResponse.LOCKED_DOOR);
                } else {
                    return new GoDirectionFailure(GoDirectionResponse.CLOSED_DOOR);
                }
            } else {
                return new GoDirectionFailure(GoDirectionResponse.WALL);
            }
        } else {
            System.out.println("Invalid direction supplied");
        }
        return new GoDirectionFailure("Unknown");
    }

    protected boolean isFirstEntrance() {
        return firstEntrance;
    }

    public void enter() {
        if (isFirstEntrance()) {
            displayFullDescription();
            firstEntrance = false;
        } else {
            displayName();
        }
    }

    public void displayFullDescription() {
        displayName();
        output.displayTextLines(getFullDescriptionLines());
        displaySearchableDescriptions();
        displayItemDescriptions();
    }

    public void displayName() {
        output.displayTextLine(getName().toString());
    }

    public void displaySearchableDescriptions() {
        searchables.forEach(s -> output.displayTextLine(s.getPositionDescription()));
    }

    public void addItem(Item item, String positionDescription) {
        items.add(item);
        itemPositionDescription.put(item, positionDescription);
    }

    public void addItemToFloor(Item item) {
        items.add(item);
        itemPositionDescription.put(item, onFloorDescription(item));
    }

    public boolean hasItem() {
        return items.size() > 0;
    }

    public Noun peekItem() {
        return items.get(0).getName();
    }

    public boolean hasItem(Noun itemName) {
        return items.stream().map(Item::getName).collect(Collectors.toList()).contains(itemName);
    }

    public Optional<Item> takeItem(GameCharacter character, Noun name) {
        if (!character.isInventoryFull()) {
            Optional<Item> item = items.stream().filter(i -> i.getName().equals(name)).findFirst();
            item.ifPresent(i -> {
                items.remove(i);
                itemPositionDescription.remove(i);
            });
            return item;
        }
        return Optional.empty();
    }

    public void displayItemDescriptions() {
        items.forEach(item -> {
            output.displayTextLine(itemPositionDescription.get(item));
        });
    }

    public void addSearchable(Interactable searchable) {
        searchables.add(searchable);
    }

    public boolean containsSearchable(Noun searchableName) {
        return getSearchable(searchableName).isPresent();
    }

    public Optional<Interactable> getSearchable(Noun searchableName) {
        return searchables.stream()
                .filter(s -> searchableName.equals(s.getName()))
                .findFirst();
    }

    public Optional<Item> search(Noun name, Adverb adverb) {
        Optional<Item> item = Optional.empty();
        Optional<Interactable> searchable = searchables.stream().filter(s -> name.equals(s.getName())).findFirst();
        if (searchable.isPresent()) {
            item = searchable.get().searchAndResolve(adverb);
        }
        return item;
    }

    public void goToSearchable(Noun searchableName) {
        getSearchable(searchableName).ifPresent(Interactable::goTo);
    }

    private String onFloorDescription(Item item) {
        return "On the floor is a " + item.getName();
    }
}
