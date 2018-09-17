package smithies.textadventure.command.state;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class FailedToParse implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    @Override
    public void run() {
        output.displayTextLine("I did not understand your command");
    }
}
