package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.interactable.climbable.ClimbResult;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class Desk extends Interactable {

    private static final SearchableName DESK = SearchableName.DESK;

    public Desk(String positionDescription) {
        super(DESK.getNoun(), positionDescription);
    }

    @Override
    public Optional<Item> searchAndResolve(Adverb adverb) {
        Optional<Item> optionalItem = search(adverb);
        switch (adverb) {
            case UNDER:
                if (optionalItem.isPresent()) {
                    output.displayTextLines("You look under the " + getName(),
                            "Yes! That's it! You grab the " + optionalItem.get().getName());
                } else {
                    output.displayTextLines("You search under the " + getName() + ". Nothing.");
                }
                break;
            case IN:
                output.displayTextLines("You are not sure how to search in the " + getName());
                break;
            case ON:
                if (optionalItem.isPresent()) {
                    output.displayTextLines("You balance on your hind legs.",
                            "Yes! That's it! You grab the " + optionalItem.get().getName());
                } else {
                    output.displayTextLines("You search on the " + getName() + ". Nothing.");
                }

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
