package smithies.textadventure.item;

import smithies.textadventure.command.Noun;

public class Kong extends DogToy {

    public Kong() {
        super(Noun.KONG);
    }

    @Override
    public String getDescription() {
        return "A Kong! It bounces every way I don't think it will.";
    }

    @Override
    public void pickUp() {

    }
}
