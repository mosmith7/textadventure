package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Noun;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class Take implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Player player;
    private Noun noun;

    public Take(Player player, Noun noun) {
        this.player = player;
        this.noun = noun;
    }

    @Override
    public void run() {
        if (player.canTakeItem(noun)) {
            player.takeItem(noun);
            output.displayTextLine("Taken.");
        }
    }
}
