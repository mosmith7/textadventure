package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;

public class Search implements GameCommandState {

    private Player player;
    private Adverb adverb;
    private Noun noun;

    public Search(Player player, Adverb adverb, Noun noun) {
        this.player = player;
        this.adverb = adverb;
        this.noun = noun;
    }

    @Override
    public void run() {
        player.search(noun, adverb);
    }
}
