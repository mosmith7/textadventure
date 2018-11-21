package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.interactable.searchable.Interactable;
import smithies.textadventure.item.Item;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.Optional;

public class PutItem implements GameCommandState {

    private Player player;
    private Noun toyName;
    private Noun searchableName;
    private DisplayOutput output = new DisplayConsoleOutput();
    private Adverb adverb;

    public PutItem(Player player, Noun toyName, Noun searchableName, Adverb adverb) {
        this.player = player;
        this.toyName = toyName;
        this.searchableName = searchableName;
        this.adverb = adverb;
    }

    @Override
    public void run() {
        Optional<Noun> playersItem = player.inventoryPeek();
        if (playersItem.isPresent()) {
            if (toyName.equals(playersItem.get())) {
                Item item = player.removeItemFromInventory(toyName).get();
                Optional<Interactable> optionalSearchable = player.getCurrentRoom().getSearchable(searchableName);
                if (optionalSearchable.isPresent()) {
                    optionalSearchable.get().addItem(item, adverb);
                    output.displayTextLine(String.format("You put the %s %s the %s", toyName, adverb, searchableName));
                } else {
                    output.displayTextLine(String.format("There is not a %s where you are", searchableName));
                }
            } else {
                output.displayTextLine(String.format("You do not have a %s to put anywhere", toyName));
            }
        } else {
            output.displayTextLine("You do not have an item to put anywhere");
        }
    }
}
