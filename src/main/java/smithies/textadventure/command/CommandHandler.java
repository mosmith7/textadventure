package smithies.textadventure.command;

import smithies.textadventure.command.state.GameCommandState;

public class CommandHandler {

    private GameCommandState currentState;

    public void processCommand() {
        currentState.run();
    }

    public GameCommandState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameCommandState currentState) {
        this.currentState = currentState;
    }
}
