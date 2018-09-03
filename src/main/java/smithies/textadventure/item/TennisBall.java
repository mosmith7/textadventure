package smithies.textadventure.item;

public class TennisBall extends DogToy {

    private ItemName name = ItemName.TENNIS_BALL;

    @Override
    public ItemName getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "A green, squishy but firm, tennis ball. My favourite!";
    }

    @Override
    public void pickUp() {

    }
}
