package smithies.textadventure.command;

import smithies.textadventure.character.Player;
import smithies.textadventure.command.state.*;
import smithies.textadventure.map.DungeonMap;

import java.util.Optional;
import java.util.Random;

public class UserInputCommand {

    private CommandCache commandCache;

    private DungeonMap map;

    private Verb verb;
    private Adverb adverb;
    private Noun noun;

    public UserInputCommand(CommandCache commandCache, DungeonMap map, Verb verb, Adverb adverb, Noun noun) {
        this.commandCache = commandCache;
        this.map = map;
        this.verb = verb;
        this.adverb = adverb;
        this.noun = noun;
    }

    public UserInputCommand(CommandCache commandCache, DungeonMap map, Verb verb, Adverb adverb) {
        this(commandCache, map, verb, adverb, null);
    }

    public UserInputCommand(CommandCache commandCache, DungeonMap map, Verb verb, Noun noun) {
        this(commandCache, map, verb, null, noun);
    }

    public UserInputCommand(CommandCache commandCache, DungeonMap map, Verb verb) {
        this(commandCache, map, verb, null, null);
    }

    public UserInputCommand(CommandCache commandCache, DungeonMap map, Adverb adverb) {
        this(commandCache, map, null, adverb, null);
    }

    public UserInputCommand(CommandCache commandCache, DungeonMap map, Noun noun) {
        this(commandCache, map, null, null, noun);
    }

    public static UserInputCommand empty(CommandCache commandCache) {
        return new UserInputCommand(commandCache);
    }

    private UserInputCommand(CommandCache commandCache) {
        this.commandCache = commandCache;

    }

    public Optional<GameCommandState> toGameCommand(Player player, DungeonMap map) {
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
        GameCommandState command = null;

        if (Verb.GO.equals(verb) && adverb == null) {
            if (noun != null) {
                command = new GoToNoun(player, noun);
            } else {
                commandCache.displayQuestionAndRetainVerb("Go where?", verb);
                questionReturned = true;
            }
        } else if ((verb == null || Verb.GO.equals(verb)) && adverbIsValidDirection(adverb)) {
            command = new GoDirection(player, adverb, map);
        } else if (Verb.WAIT.equals(verb)) {
            command = new Wait();
        } else if (Verb.EXAMINE.equals(verb)) {
            if (noun != null) {
                command = new Examine(player, noun);
            } else {
                commandCache.displayQuestionAndRetainVerb("What would you like to examine?", verb);
                questionReturned = true;
            }
        } else if (Verb.INVENTORY.equals(verb)) {
            command = new ViewInventory(player);
        } else if (Verb.TAKE.equals(verb)) {
            if (noun != null) {
                command = new Take(player, noun);
            } else {
                commandCache.displayQuestionAndRetainVerb("What would you like to take?", verb);
                questionReturned = true;
            }
        } else if (Verb.DROP.equals(verb)) {
            command = new Drop(player, noun);
        } else if (Verb.SEARCH.equals(verb) && adverb == null && noun == null) {
            command = new Look(player);
        } else if (Verb.SEARCH.equals(verb) && adverb == null) {
            // Randomly pick a type of search technique
            if (noun != null) {
                adverb = chooseRandomSearchAdverb();
                command = new Search(player, adverb, noun);
            }
        } else if (Verb.SEARCH.equals(verb) && adverbIsValidSearch(adverb)) {
            if (noun != null) {
                command = new Search(player, adverb, noun);
            } else {
                commandCache.displayQuestionAndRetainVerbAndAdverb("What would you like to search under?", verb, adverb);
                questionReturned = true;
            }
        } else if (Verb.BARK.equals(verb)) {
            command = new Bark();
        }  else if (Verb.WHINE.equals(verb)) {
            command = new Whine();
        }  else if (Verb.GROWL.equals(verb)) {
            command = new Growl();
        }  else if (Verb.SCRATCH.equals(verb)) {
            command = new Scratch(noun);
        } else if (Verb.CLIMB.equals(verb)) {
            // Deal with climbing stairs
            if (Noun.STAIRS == noun) {
                if (adverb == null) {
                    // Assume climbing up
                    command = new ClimbStairs(player, map, Adverb.UP, Adverb.NORTH, Adverb.SOUTH);
                } else {
                    if (Adverb.UP.equals(adverb)) {
                        command = new ClimbStairs(player, map, Adverb.UP, Adverb.NORTH, Adverb.SOUTH);
                    } else if (Adverb.DOWN.equals(adverb)) {
                        command = new ClimbStairs(player, map, Adverb.DOWN, Adverb.NORTH, Adverb.SOUTH);
                    }
                }
            } else {
                // Deal with climbing an object
                if (noun != null) {
                    if (adverb == null) {
                        // Assume climbing up
                        command = new ClimbObject(player, Adverb.UP, noun);
                    } else {
                        if (Adverb.UP.equals(adverb)) {
                            command = new ClimbObject(player, Adverb.UP, noun);
                        } else if (Adverb.DOWN.equals(adverb)) {
                            command = new ClimbObject(player, Adverb.DOWN, noun);
                        }
                    }
                } else if (Adverb.UP.equals(adverb) || Adverb.DOWN.equals(adverb)) {
                    commandCache.displayQuestionAndRetainVerbAndAdverb("What would you like to climb " + adverb.toString().toLowerCase() + "?", verb, adverb);
                    questionReturned = true;
                } else {
                    commandCache.displayQuestionAndRetainVerb("What would you like to climb?", verb);
                    questionReturned = true;
                }
            }
        } else if (Verb.PUT.equals(verb)) {
            // TODO: This will be tricky. Will need two nouns and adverb. e.g. put tennis ball under bed
        } else if (Verb.EXIT.equals(verb)) {
            command = new Exit();
        }  else if (Verb.FAILED_TO_PARSE.equals(verb)) {
            command = new FailedToParse();
        } else {
            command = new FailedToParse();
        }

        if (!questionReturned) {
            commandCache.clearCache();
        }

        return Optional.ofNullable(command);
    }

    private Adverb chooseRandomSearchAdverb() {
        int options = 3;
        int option = new Random().nextInt(options);
        switch (option) {
            case 0:
                return Adverb.UNDER;
            case 1:
                return Adverb.ON;
            case 2:
                return Adverb.IN;
            default:
                System.out.println("Error: Failed to choose random search adverb. Code should not get here.");
                break;
        }
        return Adverb.UNDER;
    }

    private boolean adverbIsValidDirection(Adverb adverb) {
        return Directions.ALL_DIRECTIONS.contains(adverb);
    }

    private boolean adverbIsValidSearch(Adverb adverb) {
        return Adverb.UNDER.equals(adverb) ||
                Adverb.ON.equals(adverb) ||
                Adverb.IN.equals(adverb);
    }
}
