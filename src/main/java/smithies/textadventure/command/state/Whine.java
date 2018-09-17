package smithies.textadventure.command.state;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class Whine implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    @Override
    public void run() {
        output.displayTextLine("You whine and squeek, trying to get attention");
    }
}
