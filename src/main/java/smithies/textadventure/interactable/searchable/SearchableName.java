package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Noun;

public enum SearchableName {

    SIDEBOARD(Noun.SIDEBOARD),
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
