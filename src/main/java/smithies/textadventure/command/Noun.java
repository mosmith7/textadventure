package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Noun {

    TENNIS_BALL("ball", "tennis ball"),

    WOODEN_SHELF("shelf", "wooden shelf"),
    DOG_BED("bed", "dog bed"),

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
