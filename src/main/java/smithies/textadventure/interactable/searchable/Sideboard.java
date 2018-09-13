package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.interactable.climbable.ClimbResult;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class Sideboard extends Searchable {

    private static final SearchableName SIDEBOARD = SearchableName.SIDEBOARD;

    public Sideboard(String positionDescription) {
        super(SIDEBOARD.getNoun(), positionDescription);
    }

    @Override
    public Optional<Item> tryAndSearch(Adverb adverb) {
        switch (adverb) {
            case UNDER:
                output.displayTextLines("You shove your nose under the shelf.",
                        "You're sure you can smell something you want!");
                break;
            case IN:
                output.displayTextLines("You search in the shelf. Nothing.");
                break;
            case ON:
                output.displayTextLines("You search on the shelf. Nothing.");
                break;
            default:
                break;
        }

        return Optional.empty();
    }

    @Override
    public ClimbResult attemptClimb() {
        return new ClimbResult(true, "It isn't graceful, but you scramble up onto the " + getName());
    }


}
