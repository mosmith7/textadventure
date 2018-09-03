package smithies.textadventure.rooms;

import smithies.textadventure.item.Item;
import smithies.textadventure.item.ItemName;
import smithies.textadventure.session.Player;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Room {

    protected DisplayOutput output = new DisplayConsoleOutput();
    protected boolean isFirstEntrance = true;
    protected List<Item> items = new ArrayList<>();
    protected Map<Item, String> itemPositionDescription = new HashMap<>();

    public abstract RoomName getName();

    public abstract String[] getFullDescriptionLines();

    public abstract RoomName goNorth();

    public abstract RoomName goSouth();

    public abstract RoomName goEast();

    public abstract RoomName goWest();

    protected boolean isFirstEntrance() {
        return isFirstEntrance;
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
        displayItemDescriptions();
    }

    public void displayName() {
        output.displayTextLine(getName().toString());
    }

    public void displayItemDescriptions() {
        items.forEach(item -> {
            output.displayTextLine(itemPositionDescription.get(item));
        });
    }
}
