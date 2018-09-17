package smithies.textadventure.command.state;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.Noun;
import smithies.textadventure.rooms.Room;
import smithies.textadventure.ui.DisplayConsoleOutput;

public class GoToNoun implements GameCommandState {

    private DisplayConsoleOutput output = new DisplayConsoleOutput();

    private Player player;
    private Noun noun;

    public GoToNoun(Player player, Noun noun) {
        this.player = player;
        this.noun = noun;
    }

    @Override
    public void run() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.containsSearchable(noun)) {
            currentRoom.goToSearchable(noun);
        } else {
            output.displayTextLine("Are you sure " + noun + " is in the same room as you?");
        }
    }
}
