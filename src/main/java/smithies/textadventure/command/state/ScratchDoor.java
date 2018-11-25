package smithies.textadventure.command.state;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.rooms.partition.Door;
import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public class ScratchDoor implements GameCommandState {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Adverb direction;
    private Door door;

    public ScratchDoor(Door door, Adverb direction) {
        this.door = door;
        this.direction = direction;
    }

    public Adverb getDirection() {
        return direction;
    }

    public Door getDoor() {
        return door;
    }

    @Override
    public void run() {
        if (door.isLocked()) {
            output.displayTextLine(String.format("You scratch the locked, %s, door", direction));
        } else if (door.isOpen()) {
            output.displayTextLine(String.format("You scratch the open, %s, door", direction));
        } else {
            output.displayTextLine(String.format("You scratch the closed, %s, door", direction));
        }
    }
}
