package smithies.textadventure.rooms;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Item;
import smithies.textadventure.item.ItemName;
import smithies.textadventure.searchable.Searchable;
import smithies.textadventure.session.Player;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Room {

    private DisplayOutput output = new DisplayConsoleOutput();
    private boolean isFirstEntrance = true;
    private List<Item> items = new ArrayList<>();
    private Map<Item, String> itemPositionDescription = new HashMap<>();
    private List<Searchable> searchables = new ArrayList<>();

    public abstract RoomName getName();

    public abstract String[] getFullDescriptionLines();

    public abstract RoomName goNorth();

    public abstract RoomName goSouth();

    public abstract RoomName goEast();

    public abstract RoomName goWest();

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

    public boolean hasItem(ItemName itemname) {
        return items.stream().map(Item::getName).collect(Collectors.toList()).contains(itemname);
    }

    public Optional<Item> takeItem(Player player, ItemName name) {
        if (!player.isInventoryFull()) {
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

    public void addSearchable(Searchable searchable) {
        searchables.add(searchable);
    }

    public Optional<Item> search(Noun name, Adverb adverb) {
        Optional<Item> item = Optional.empty();
        Optional<Searchable> searchable = searchables.stream().filter(s -> name.equals(s.getName())).findFirst();
        if (searchable.isPresent()) {
            item = searchable.get().tryAndSearch(adverb);
        }
        return item;
    }
}
