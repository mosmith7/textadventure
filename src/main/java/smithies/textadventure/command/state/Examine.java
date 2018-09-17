package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Noun;

public class Examine implements GameCommandState {

    private Player player;
    private Noun noun;

    public Examine(Player player, Noun noun) {
        this.player = player;
        this.noun = noun;
    }

    @Override
    public void run() {
        System.out.println("TODO: implement examine");
    }
}
