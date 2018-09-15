package smithies.textadventure.command;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Directions {

    // TODO: should this be an enum of it's own?

    public static final List<Adverb> ALL_DIRECTIONS = Arrays.asList(Adverb.NORTH, Adverb.EAST, Adverb.SOUTH, Adverb.WEST);

    public static Adverb getRandomDirection() {
        int options = ALL_DIRECTIONS.size();
        int option = new Random().nextInt(options);
        Adverb adverb = Adverb.NORTH;
        switch (option) {
            case 0:
                adverb = Adverb.NORTH;
                break;
            case 1:
                adverb = Adverb.EAST;
                break;
            case 2:
                adverb = Adverb.SOUTH;
                break;
            case 3:
                adverb = Adverb.WEST;
                break;
            default:
                System.out.println("Error: Failed to choose random direction adverb. Code should not get here.");
                break;
        }
        return adverb;
    }
}
