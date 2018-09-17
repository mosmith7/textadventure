package smithies.textadventure.command.state;

import smithies.textadventure.ui.DisplayConsoleOutput;

public class Wait implements GameCommandState {

    private DisplayConsoleOutput output = new DisplayConsoleOutput();

    @Override
    public void run() {
        output.displayTextLine("You scratch your ears with your leg");
    }
}
