package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Adverb {

    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west"),

    UNDER("under"),
    ON("on"),
    IN("in"),

    ;

    private List<String> aliases = new ArrayList<>();

    Adverb(String... aliasArray) {
        for (int i = 0; i < aliasArray.length; i++) {
            aliases.add(" " + aliasArray[i].toUpperCase() + " ");
        }
    }

    public List<String> getAliases() {
        return aliases;
    }
}
