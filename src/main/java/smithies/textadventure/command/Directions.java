package smithies.textadventure.command;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Directions {

    // TODO: should this be an enum of it's own?

    public static final List<Adverb> MAIN_COMPASS_DIRECTIONS = Arrays.asList(Adverb.NORTH, Adverb.EAST, Adverb.SOUTH,
            Adverb.WEST);

    public static final List<Adverb> MINOR_COMPASS_DIRECTIONS = Arrays.asList(Adverb.NORTH_EAST, Adverb.NORTH_WEST,
            Adverb.SOUTH_EAST, Adverb.SOUTH_WEST);

    public static final List<Adverb> ALL_DIRECTIONS = Stream.concat(MAIN_COMPASS_DIRECTIONS.stream(), MINOR_COMPASS_DIRECTIONS.stream())
            .collect(Collectors.toList());

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
            case 4:
                adverb = Adverb.NORTH_EAST;
                break;
            case 5:
                adverb = Adverb.NORTH_WEST;
                break;
            case 6:
                adverb = Adverb.SOUTH_EAST;
                break;
            case 7:
                adverb = Adverb.SOUTH_WEST;
                break;
            default:
                System.out.println("Error: Failed to choose random direction adverb. Code should not get here.");
                break;
        }
        return adverb;
    }
}
