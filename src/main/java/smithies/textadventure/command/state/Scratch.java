package smithies.textadventure.command.state;

import smithies.textadventure.command.Noun;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class Scratch implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Noun noun;

    public Scratch(Noun noun) {
        this.noun = noun;
    }

    @Override
    public void run() {
        if (noun != null) {
            output.displayTextLine("You scratch the " + noun.toString());
        } else {
            output.displayTextLine("You scratch the floor");
        }
    }
}
