package smithies.textadventure.item;

import smithies.textadventure.rooms.RoomName;

public abstract class Item {

    public abstract ItemName getName();

    public abstract String getDescription();

    public abstract void pickUp();
}
