package smithies.textadventure.command;

import java.util.Optional;
import java.util.Random;

public class UserInputCommand {

    private CommandCache commandCache;

    private Verb verb;
    private Adverb adverb;
    private Noun noun;

    public UserInputCommand(CommandCache commandCache, Verb verb, Adverb adverb, Noun noun) {
        this.commandCache = commandCache;
        this.verb = verb;
        this.adverb = adverb;
        this.noun = noun;
    }

    public UserInputCommand(CommandCache commandCache, Verb verb, Adverb adverb) {
        this(commandCache, verb, adverb, null);
    }

    public UserInputCommand(CommandCache commandCache, Verb verb, Noun noun) {
        this(commandCache, verb, null, noun);
    }

    public UserInputCommand(CommandCache commandCache, Verb verb) {
        this(commandCache, verb, null, null);
    }

    public UserInputCommand(CommandCache commandCache, Adverb adverb) {
        this(commandCache, null, adverb, null);
    }

    public UserInputCommand(CommandCache commandCache, Noun noun) {
        this(commandCache, null, null, noun);
    }

    public static UserInputCommand empty(CommandCache commandCache) {
        return new UserInputCommand(commandCache);
    }

    private UserInputCommand(CommandCache commandCache) {
        this.commandCache = commandCache;

    }

    public Optional<GameCommand> toGameCommand() {
        // Get any cached commands from when a question was asked
        if (verb == null && adverb == null) {
            Optional<Verb> cachedVerb = commandCache.getCachedVerb();
            Optional<Adverb> cachedAdverb = commandCache.getCachedAdverb();
            if (cachedVerb.isPresent() && cachedAdverb.isPresent()) {
                verb = cachedVerb.get();
                adverb = cachedAdverb.get();
            }
        } else if (verb == null) {
            commandCache.getCachedVerb().ifPresent(v -> verb = v);
        }

        boolean questionReturned = false;
        GameCommand command = null;

        if (Verb.GO.equals(verb) && adverb == null) {
            if (noun != null) {
                command = GameCommand.GO_TO_NOUN;
                command.setNoun(noun);
            } else {
                commandCache.displayQuestionAndRetainVerb("Go where?", verb);
                questionReturned = true;
            }
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.NORTH.equals(adverb)) {
            command = GameCommand.NORTH;
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.SOUTH.equals(adverb)) {
            command = GameCommand.SOUTH;
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.EAST.equals(adverb)) {
            command = GameCommand.EAST;
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.WEST.equals(adverb)) {
            command = GameCommand.WEST;
        } else if (Verb.WAIT.equals(verb)) {
            command = GameCommand.WAIT;
        } else if (Verb.EXAMINE.equals(verb)) {
            if (noun != null) {
                command = GameCommand.EXAMINE;
                command.setNoun(noun);
            } else {
                commandCache.displayQuestionAndRetainVerb("What would you like to examine?", verb);
                questionReturned = true;
            }
        } else if (Verb.INVENTORY.equals(verb)) {
            command = GameCommand.INVENTORY;
        } else if (Verb.TAKE.equals(verb)) {
            if (noun != null) {
                command = GameCommand.TAKE;
                command.setNoun(noun);
            } else {
                commandCache.displayQuestionAndRetainVerb("What would you like to take?", verb);
                questionReturned = true;
            }
        } else if (Verb.DROP.equals(verb)) {
            command = GameCommand.DROP;
        } else if (Verb.SEARCH.equals(verb) && adverb == null && noun == null) {
            command = GameCommand.LOOK;
        } else if (Verb.SEARCH.equals(verb) && adverb == null) {
            // Randomly pick a type of search technique
            if (noun != null) {
                command = chooseRandomSearchCommand();
                command.setNoun(noun);
            }
        } else if (Verb.SEARCH.equals(verb) && Adverb.UNDER.equals(adverb)) {
            if (noun != null) {
                command = GameCommand.SEARCH_UNDER;
                command.setNoun(noun);
            } else {
                commandCache.displayQuestionAndRetainVerbAndAdverb("What would you like to search under?", verb, adverb);
                questionReturned = true;
            }
        } else if (Verb.SEARCH.equals(verb) && Adverb.IN.equals(adverb)) {
            if (noun != null) {
                command = GameCommand.SEARCH_IN;
                command.setNoun(noun);
            } else {
                commandCache.displayQuestionAndRetainVerbAndAdverb("What would you like to search in?", verb, adverb);
                questionReturned = true;
            }
        } else if (Verb.SEARCH.equals(verb) && Adverb.ON.equals(adverb)) {
            if (noun != null) {
                command = GameCommand.SEARCH_ON;
                command.setNoun(noun);
            } else {
                commandCache.displayQuestionAndRetainVerbAndAdverb("What would you like to search on?", verb, adverb);
                questionReturned = true;
            }
        } else if (Verb.BARK.equals(verb)) {
            command = GameCommand.BARK;
        }  else if (Verb.WHINE.equals(verb)) {
            command = GameCommand.WHINE;
        }  else if (Verb.GROWL.equals(verb)) {
            command = GameCommand.GROWL;
        }  else if (Verb.SCRATCH.equals(verb)) {
            GameCommand scratch = GameCommand.SCRATCH;
            if (noun != null) {
                scratch.setNoun(noun);
            }
            command = scratch;
        }  else if (Verb.EXIT.equals(verb)) {
            command = GameCommand.EXIT;
        }  else if (Verb.FAILED_TO_PARSE.equals(verb)) {
            command = GameCommand.FAILED_TO_PARSE;
        } else {
            command = GameCommand.FAILED_TO_PARSE;
        }

        if (!questionReturned) {
            commandCache.clearCache();
        }

        return Optional.ofNullable(command);
    }

    private GameCommand chooseRandomSearchCommand() {
        int options = 3;
        int option = new Random().nextInt(options);
        switch (option) {
            case 0:
                return GameCommand.SEARCH_UNDER;
            case 1:
                return GameCommand.SEARCH_ON;
            case 2:
                return GameCommand.SEARCH_IN;
            default:
                System.out.println("Error: Failed to choose random search adverb. Code should not get here.");
                break;
        }
        return GameCommand.SEARCH_UNDER;
    }
}
