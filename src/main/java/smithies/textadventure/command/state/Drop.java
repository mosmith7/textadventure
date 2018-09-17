package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Noun;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class Drop implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Player player;
    private Noun noun;

    public Drop(Player player, Noun noun) {
        this.player = player;
        this.noun = noun;
    }

    @Override
    public void run() {
        if (player.isInventoryEmpty()) {
            output.displayTextLine("You have nothing to drop");
        } else {
            if (noun != null) {
                player.inventoryPeek().ifPresent(itemName -> {
                    if (itemName.equals(noun)) {
                        player.dropItem(itemName);
                        output.displayTextLine("Dropped.");
                    }
                });
            } else {
                player.inventoryPeek().ifPresent(itemName -> {
                    player.dropItem(itemName);
                    output.displayTextLine("Dropped " + itemName.name());
                });
            }
        }
    }
}
