package smithies.textadventure.interactable.searchable;

import smithies.textadventure.character.npc.Npc;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Nameable;
import smithies.textadventure.command.Noun;
import smithies.textadventure.interactable.climbable.Climbable;
import smithies.textadventure.item.Item;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.*;

public abstract class Interactable extends Nameable implements Searchable, Climbable {

    protected DisplayOutput output = new DisplayConsoleOutput();
    private String positionDescription;
    private Map<Adverb, List<Item>> itemsByAdverb = new HashMap<>();

    protected Interactable(Noun name, String positionDescription) {
        super(name);
        this.positionDescription = positionDescription;
        itemsByAdverb.put(Adverb.ON, new ArrayList<>());
        itemsByAdverb.put(Adverb.UNDER, new ArrayList<>());
        itemsByAdverb.put(Adverb.IN, new ArrayList<>());
    }

    public Optional<Item> search(Adverb adverb) {
        // Intended to be called from searchAndResolve method
        List<Item> items = itemsByAdverb.getOrDefault(adverb, new ArrayList<>());
        return Optional.ofNullable(items.get(0));
    }

    public List<Noun> peek() {
        List<Noun> itemNames = new ArrayList<>();
        itemsByAdverb.keySet().forEach(key -> {
            List<Item> items = itemsByAdverb.get(key);
            for (Item item : items) {
                itemNames.add(item.getName());
            }
        });
        return itemNames;
    }

    public Optional<Item> pop(Npc npc) {
        for (Adverb key : itemsByAdverb.keySet()) {
            List<Item> items = itemsByAdverb.get(key);
            if (!items.isEmpty()) {
                Item itemToReturn = items.get(0);
                items.remove(itemToReturn);
                output.displayTextLine(String.format("%s takes a %s from %s the %s",
                        npc.getNameForSasha(), itemToReturn.getName(), key, getName()));
                return Optional.of(itemToReturn);
            }
        }
        return Optional.empty();
    }

    public void addItem(Item item, Adverb adverb) {
        List<Item> items = itemsByAdverb.get(adverb);
        if (items == null) items = new ArrayList<>();
        items.add(item);
        itemsByAdverb.put(adverb, items);
    }

    public String getPositionDescription() {
        return positionDescription;
    }

    public void displayItemDescription(Adverb adverb) {
        itemsByAdverb.get(adverb).forEach(item -> {
            output.displayTextLine(String.format("%s the %s is a %s", adverb, getName(), item.getName()));
        });
    }

    public void goTo() {
        output.displayTextLine("You walk over to the " + getName());
    }

    public void displayClimbUpSuccess() {
        output.displayTextLine("You successfully climb up onto the top of the " + getName());
    }

    public void displayClimbDownSuccess() {
        output.displayTextLine("You successfully climb down off of the " + getName());
    }

}
