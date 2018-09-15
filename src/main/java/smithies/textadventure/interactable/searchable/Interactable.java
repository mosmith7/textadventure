package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Nameable;
import smithies.textadventure.command.Noun;
import smithies.textadventure.interactable.climbable.Climbable;
import smithies.textadventure.item.Item;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Interactable extends Nameable implements Searchable, Climbable {

    protected DisplayOutput output = new DisplayConsoleOutput();
    private String positionDescription;
    private Map<Adverb, Item> itemByAdverb = new HashMap<>();

    protected Interactable(Noun name, String positionDescription) {
        super(name);
        this.positionDescription = positionDescription;
    }

    public Optional<Item> search(Adverb adverb) {
        // Intended to be called from searchAndResolve method
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

    public void displayClimbUpSuccess() {
        output.displayTextLine("You successfully climb up onto the top of the " + getName());
    }

    public void displayClimbDownSuccess() {
        output.displayTextLine("You successfully climb down off of the " + getName());
    }

}
