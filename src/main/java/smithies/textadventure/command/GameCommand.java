package smithies.textadventure.command;

public enum GameCommand {

    GO_TO_NOUN(Verb.GO, null),

    NORTH(Verb.GO, Adverb.NORTH),
    SOUTH(Verb.GO, Adverb.SOUTH),
    EAST(Verb.GO, Adverb.EAST),
    WEST(Verb.GO, Adverb.WEST),

    WAIT(Verb.WAIT, null),
    LOOK(Verb.SEARCH, null),

    EXAMINE(Verb.EXAMINE, null),
    INVENTORY(Verb.INVENTORY, null),
    TAKE(Verb.TAKE, null),
    DROP(Verb.DROP, null),

    SEARCH_UNDER(Verb.SEARCH, Adverb.UNDER),
    SEARCH_IN(Verb.SEARCH, Adverb.IN),
    SEARCH_ON(Verb.SEARCH, Adverb.ON),

    BARK(Verb.BARK, null),
    WHINE(Verb.WHINE, null),
    GROWL(Verb.GROWL, null),
    SCRATCH(Verb.SCRATCH, null),

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

    public Noun getAndResetNoun() {
        Noun noun = this.noun;
        this.noun = null;
        return noun;
    }

    public Verb getVerb() {
        return verb;
    }

    public Adverb getAdverb() {
        return adverb;
    }
}
