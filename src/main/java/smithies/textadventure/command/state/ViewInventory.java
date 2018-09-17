package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;

public class ViewInventory implements GameCommandState {

    private Player player;

    public ViewInventory(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.viewInventory();
    }
}
