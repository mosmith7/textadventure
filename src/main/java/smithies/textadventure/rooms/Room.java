package smithies.textadventure.rooms;

import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Item;
import smithies.textadventure.interactable.searchable.Interactable;
import smithies.textadventure.character.GameCharacter;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Room {

    private DisplayOutput output = new DisplayConsoleOutput();
    private boolean isFirstEntrance = true;
    private List<Item> items = new ArrayList<>();
    private Map<Item, String> itemPositionDescription = new HashMap<>();
    private List<Interactable> searchables = new ArrayList<>();

    public abstract RoomName getName();

    public abstract String[] getFullDescriptionLines();

    public abstract RoomName goNorth();

    public abstract RoomName goSouth();

    public abstract RoomName goEast();

    public abstract RoomName goWest();

    public Optional<RoomName> goDirection(Adverb direction) {
        RoomName roomName;
        switch (direction) {
            case NORTH:
                roomName = goNorth();
                break;
            case EAST:
                roomName = goEast();
                break;
            case SOUTH:
                roomName = goSouth();
                break;
            case WEST:
                roomName = goWest();
                break;
            default:
                throw new RuntimeException("Invalid direction supplied");
        }
        return Optional.ofNullable(roomName);
    }

    protected boolean isFirstEntrance() {
        return isFirstEntrance;
    }

    public void enter() {
        if (isFirstEntrance()) {
            displayFullDescription();
            isFirstEntrance = false;
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
