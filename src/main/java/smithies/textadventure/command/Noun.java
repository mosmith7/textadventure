package smithies.textadventure.command;

import java.util.ArrayList;
import java.util.List;

public enum Noun {

    STAIRS("stairs"),

    TENNIS_BALL("tennis ball", "ball"),
    KONG("kong"),
    BALL_AND_ROPE("ball and rope", "ballandrope"),

    SIDEBOARD("sideboard"),
    DOG_BED("dog bed", "bed"),
    PIANO("piano", "keyboard"),
    DESK("desk"),

    DOOR("door"),

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
