package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Adverb;

public class ClimbStairs implements GameCommandState {

    private Player player;
    private Adverb verticalDirection;
    private Adverb upCompassDirection;
    private Adverb downCompassDirection;

    public ClimbStairs(Player player, Adverb verticalDirection,
                       Adverb upStairsDirection, Adverb downStairsDirection) {
        this.player = player;
        this.verticalDirection = verticalDirection;
        this.upCompassDirection = upStairsDirection;
        this.downCompassDirection = downStairsDirection;
    }

    @Override
    public void run() {
        switch (verticalDirection) {
            case UP:
                player.goDirection(upCompassDirection);
                break;
            case DOWN:
                player.goDirection(downCompassDirection);
                break;
            default:
                //TODO: handle
                break;
        }
    }
}
