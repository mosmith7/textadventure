package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Adverb {

    NORTH("north", "n"),
    SOUTH("south", "s"),
    EAST("east", "e"),
    WEST("west", "w"),
    NORTH_EAST("northeast", "north-east", "ne"),
    SOUTH_EAST("southeast", "south-east", "se"),
    NORTH_WEST("northwest", "north-west", "nw"),
    SOUTH_WEST("southwest", "south-west", "sw"),

    UP("up"),
    DOWN("down"),

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
