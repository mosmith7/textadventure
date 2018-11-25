package smithies.textadventure.interactable.searchable;

import smithies.textadventure.interactable.climbable.ClimbResult;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class Piano extends Interactable {

    private static final SearchableName PIANO = SearchableName.PIANO;

    public Piano(String positionDescription) {
        super(PIANO.getNoun(), positionDescription);
    }

    @Override
    public void searchUnder(Optional<Item> optionalItem) {
        if (optionalItem.isPresent()) {
            output.displayTextLines("You look under the " + getName(),
                    "Yes! That's it! You grab the " + optionalItem.get().getName());
        } else {
            output.displayTextLines("You search under the " + getName() + ". Nothing.");
        }
    }

    @Override
    public ClimbResult attemptClimb() {
        return new ClimbResult(true, "It isn't graceful, but you scramble up onto the " + getName());
    }
}
