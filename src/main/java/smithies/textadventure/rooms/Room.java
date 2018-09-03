package smithies.textadventure.rooms;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public abstract class Room {

    protected DisplayOutput output = new DisplayConsoleOutput();
    protected boolean isFirstEntrance = true;

    protected boolean isFirstEntrance() {
        return isFirstEntrance;
    }

    public void enter() {
        if (isFirstEntrance()) {
            displayFullDescription();
            isFirstEntrance = false;
        } else {
            displayName();
        }
    }

    public abstract RoomName getName();

    public abstract void displayFullDescription();

    public void displayName() {
        output.displayTextLine(getName().toString());
    }

    public abstract RoomName goNorth();

    public abstract RoomName goSouth();

    public abstract RoomName goEast();

    public abstract RoomName goWest();
}
