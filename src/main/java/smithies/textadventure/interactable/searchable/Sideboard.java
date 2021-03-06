package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.interactable.climbable.ClimbResult;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class Sideboard extends Interactable {

    private static final SearchableName SIDEBOARD = SearchableName.SIDEBOARD;

    public Sideboard(String positionDescription) {
        super(SIDEBOARD.getNoun(), positionDescription);
    }

    @Override
    public void searchUnder(Optional<Item> optionalItem) {
        if (optionalItem.isPresent()) {
            output.displayTextLines("You shove your nose under the " + getName() + ".",
                    "You're sure you can smell something you want!");
        } else {
            searchFail(Adverb.UNDER);
        }
    }

    @Override
    public ClimbResult attemptClimb() {
        return new ClimbResult(true, "It isn't graceful, but you scramble up onto the " + getName());
    }


}
