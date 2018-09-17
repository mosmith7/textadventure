package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;

public class Look implements GameCommandState {

    private Player player;

    public Look(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.look();
    }
}
