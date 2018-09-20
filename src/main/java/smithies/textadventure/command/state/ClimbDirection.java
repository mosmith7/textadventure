package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;

public class ClimbDirection implements GameCommandState {

    private Player player;
    private Adverb direction;
    private Noun noun;

    public ClimbDirection(Player player, Adverb direction) {
       this(player, direction, null);
    }

    public ClimbDirection(Player player, Adverb direction, Noun noun) {
        this.player = player;
        this.direction = direction;
        this.noun = noun;
    }

    @Override
    public void run() {
        switch (direction) {
            case UP:
                if (noun != null) {
                    player.climbUp(noun);
                }
                break;
            case DOWN:
                if (noun != null) {
                    player.climbDown(noun);
                } else {
                    player.climbDown();
                }
                break;
            default:
                //TODO: handle
                break;
        }
    }
}
