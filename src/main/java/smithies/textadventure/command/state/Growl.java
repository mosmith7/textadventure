package smithies.textadventure.command.state;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class Growl implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    @Override
    public void run() {
        output.displayTextLine("You growl menacingly");
    }
}
