package smithies.textadventure.command;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.Random;

public class UserInputCommand {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Verb verb;
    private Adverb adverb;
    private Noun noun;

    public UserInputCommand(Verb verb, Adverb adverb, Noun noun) {
        this.verb = verb;
        this.adverb = adverb;
        this.noun = noun;
    }

    public UserInputCommand(Verb verb, Adverb adverb) {
        this(verb, adverb, null);
    }

    public UserInputCommand(Verb verb, Noun noun) {
        this(verb, null, noun);
    }

    public UserInputCommand(Verb verb) {
        this(verb, null, null);
    }

    public UserInputCommand(Adverb adverb) {
        this(null, adverb, null);
    }

    public GameCommand toGameCommand() {
        if (Verb.GO.equals(verb) && adverb == null) {
            output.displayTextLine("Go where?");
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.NORTH.equals(adverb)) {
            return GameCommand.NORTH;
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.SOUTH.equals(adverb)) {
            return GameCommand.SOUTH;
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.EAST.equals(adverb)) {
            return GameCommand.EAST;
        } else if ((verb == null || Verb.GO.equals(verb)) && Adverb.WEST.equals(adverb)) {
            return GameCommand.WEST;
        } else if (Verb.WAIT.equals(verb)) {
            return GameCommand.WAIT;
        } else if (Verb.LOOK.equals(verb)) {
            return GameCommand.LOOK;
        } else if (Verb.EXAMINE.equals(verb)) {
            if (noun != null) {
                GameCommand command = GameCommand.EXAMINE;
                command.setNoun(noun);
                return command;
            } else {
                output.displayTextLine("What would you like to examine?");
            }
        } else if (Verb.INVENTORY.equals(verb)) {
            return GameCommand.INVENTORY;
        } else if (Verb.TAKE.equals(verb)) {
            return GameCommand.TAKE;
        } else if (Verb.DROP.equals(verb)) {
            return GameCommand.DROP;
        } else if (Verb.SEARCH.equals(verb) && adverb == null) {
            // Randomly pick a type of search technique
            if (noun != null) {
                GameCommand command = chooseRandomSearchCommand();
                command.setNoun(noun);
                return command;
            } else {
                output.displayTextLine("What would you like to search?");
            }
        } else if (Verb.SEARCH.equals(verb) && Adverb.UNDER.equals(adverb)) {
            if (noun != null) {
                GameCommand command = GameCommand.SEARCH_UNDER;
                command.setNoun(noun);
                return command;
            } else {
                output.displayTextLine("What would you like to search under?");
            }
        } else if (Verb.SEARCH.equals(verb) && Adverb.IN.equals(adverb)) {
            if (noun != null) {
                GameCommand command = GameCommand.SEARCH_IN;
                command.setNoun(noun);
                return command;
            } else {
                output.displayTextLine("What would you like to search in?");
            }
        } else if (Verb.SEARCH.equals(verb) && Adverb.ON.equals(adverb)) {
            if (noun != null) {
                GameCommand command = GameCommand.SEARCH_ON;
                command.setNoun(noun);
                return command;
            } else {
                output.displayTextLine("What would you like to search on?");
            }
        } else if (Verb.EXIT.equals(verb)) {
            return GameCommand.EXIT;
        }
        return GameCommand.FAILED_TO_PARSE;
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
