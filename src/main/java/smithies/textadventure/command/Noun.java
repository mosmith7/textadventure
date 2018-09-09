package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Noun {

    TENNIS_BALL("tennis ball", "ball"),

    WOODEN_SHELF("wooden shelf", "shelf"),
    DOG_BED("dog bed", "bed"),

    ;

    private List<String> aliases = new ArrayList<>();

    Noun(String... aliasArray) {
        for (int i = 0; i < aliasArray.length; i++) {
            aliases.add(" " + aliasArray[i].toUpperCase() + " ");
        }
    }

    public List<String> getAliases() {
        return aliases;
    }
}
