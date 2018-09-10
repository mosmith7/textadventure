package smithies.textadventure.searchable;

import smithies.textadventure.command.Noun;

public enum SearchableName {

    WOODEN_SHELF(Noun.WOODEN_SHELF),
    DOG_BED(Noun.DOG_BED),

    ;

    private Noun noun;

    SearchableName(Noun noun) {
        this.noun = noun;
    }

    public Noun getNoun() {
        return noun;
    }
}
