package smithies.textadventure.command;

public enum GameCommand {

    NORTH(Verb.GO, Adverb.NORTH),
    SOUTH(Verb.GO, Adverb.SOUTH),
    EAST(Verb.GO, Adverb.EAST),
    WEST(Verb.GO, Adverb.WEST),

    WAIT(Verb.WAIT, null),
    LOOK(Verb.GO, null),

    EXAMINE(Verb.EXAMINE, null),
    INVENTORY(Verb.INVENTORY, null),
    TAKE(Verb.TAKE, null),
    DROP(Verb.DROP, null),

    SEARCH_UNDER(Verb.SEARCH, Adverb.UNDER),
    SEARCH_IN(Verb.SEARCH, Adverb.IN),
    SEARCH_ON(Verb.SEARCH, Adverb.ON),

    EXIT(Verb.EXIT, null),

    FAILED_TO_PARSE(null, null),
    ;

    private Verb verb;
    private Adverb adverb;
    private Noun noun;

    GameCommand(Verb verb, Adverb adverb) {
        this.verb = verb;
        this.adverb = adverb;
    }

    public void setNoun(Noun noun) {
        this.noun = noun;
    }

    public Noun getNoun() {
        return noun;
    }

    public Verb getVerb() {
        return verb;
    }

    public Adverb getAdverb() {
        return adverb;
    }
}
