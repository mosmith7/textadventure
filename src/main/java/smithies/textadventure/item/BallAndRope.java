package smithies.textadventure.item;

import smithies.textadventure.command.Noun;

public class BallAndRope extends DogToy {

    public BallAndRope() {
        super(Noun.BALL_AND_ROPE);
    }

    @Override
    public String getDescription() {
        return "A Kong! It bounces every way I don't think it will.";
    }

    @Override
    public void pickUp() {

    }
}