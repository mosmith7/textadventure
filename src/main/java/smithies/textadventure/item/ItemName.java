package smithies.textadventure.item;

import smithies.textadventure.command.Noun;

public enum ItemName {

    // Items
    TENNIS_BALL(Noun.TENNIS_BALL),

    ;

    private Noun noun;

    ItemName(Noun noun) {
        this.noun = noun;
    }

    public Noun getNoun() {
        return noun;
    }

}
