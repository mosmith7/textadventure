package smithies.textadventure.rooms;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

public abstract class Room {

    protected DisplayOutput output = new DisplayConsoleOutput();

    public abstract RoomName getName();

    public abstract boolean isFirstEntrance();

    public abstract void enter();

    public abstract void displayFullDescription();

    public void displayName() {
        output.displayTextLine(getName().toString());
    }

    public abstract RoomName goNorth();

    public abstract RoomName goSouth();

    public abstract RoomName goEast();

    public abstract RoomName goWest();
}
