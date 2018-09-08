package smithies.textadventure.item;

import smithies.textadventure.command.Noun;

public class TennisBall extends DogToy {

    public TennisBall() {
        super(Noun.TENNIS_BALL);
    }

    @Override
    public String getDescription() {
        return "A green, squishy but firm, tennis ball. My favourite!";
    }

    @Override
    public void pickUp() {

    }
}
