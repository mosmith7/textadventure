package smithies.textadventure.item;

import smithies.textadventure.command.Nameable;
import smithies.textadventure.command.Noun;

public abstract class Item extends Nameable {

    public Item(Noun name) {
        super(name);
    }

    public abstract String getDescription();

    public abstract void pickUp();
}
