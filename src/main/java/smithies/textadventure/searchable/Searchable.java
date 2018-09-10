package smithies.textadventure.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Nameable;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Item;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Searchable extends Nameable {

    protected DisplayOutput output = new DisplayConsoleOutput();
    private String positionDescription;
    private Map<Adverb, Item> itemByAdverb = new HashMap<>();

    protected Searchable(Noun name, String positionDescription) {
        super(name);
        this.positionDescription = positionDescription;
    }

    public abstract Optional<Item> tryAndSearch(Adverb adverb);

    protected Optional<Item> search(Adverb adverb) {
        // Intended to be called from tryAndSearch method
        Item item = itemByAdverb.getOrDefault(adverb, null);
        return Optional.ofNullable(item);
    }

    public void addItem(Item item, Adverb adverb) {
        itemByAdverb.put(adverb, item);
    }

    public String getPositionDescription() {
        return positionDescription;
    }

    public void goTo() {
        output.displayTextLine("You walk over to the " + getName());
    }

}
