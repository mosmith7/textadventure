package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Verb {

    GO("move", "go", "walk"),
    CLIMB("climb"),

    WAIT("wait"),

    EXAMINE("examine"),
    INVENTORY("inventory"),
    TAKE("take", "grab", "pick up"),
    DROP("drop", "release"),

    SEARCH("search", "look"),

    BARK("woof", "bark"),
    WHINE("whine", "squeek"),
    GROWL("growl", "gr", "grr", "grrr", "grrrr"),
    SCRATCH("scratch"),

    FAILED_TO_PARSE,

    EXIT("exit", "quit"),

    ;

    private List<String> aliases = new ArrayList<>();

    Verb(String... aliasArray) {
        for (int i = 0; i < aliasArray.length; i++) {
            aliases.add(" " + aliasArray[i].toUpperCase() + " ");
        }
    }

    public List<String> getAliases() {
        return aliases;
    }
}
