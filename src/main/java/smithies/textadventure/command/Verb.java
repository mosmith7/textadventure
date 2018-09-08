package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Verb {

    GO("move", "go"),

    WAIT("wait"),
    LOOK("look"),

    EXAMINE("examine"),
    INVENTORY("inventory"),
    TAKE("take", "grab"),
    DROP("drop"),

    SEARCH("search"),

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
