package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class DogBed extends Interactable {

    private static final Noun DOG_BED = Noun.DOG_BED;

    public DogBed(String positionDescription) {
        super(DOG_BED, positionDescription);
    }

    @Override
    public void searchUnder(Optional<Item> optionalItem) {
        if (optionalItem.isPresent()) {
            output.displayTextLines("You get your entire head under the soft bed.",
                    "Yes! That's it! You grab the " + optionalItem.get().getName());
        } else {
            searchFail(Adverb.UNDER);
        }
    }

    @Override
    public void searchIn(Optional<Item> optionalItem) {
        if (optionalItem.isPresent()) {
            output.displayTextLines("You rip open the bed with your teeth, stuff your head in it and have a good rummage.",
                    "Yes! That's it! You grab the " + optionalItem.get().getName());
        } else {
            output.displayTextLines("You rip open the bed with your teeth, stuff your head in it and have a good rummage.",
                    "Unfortunately you don't find anything.");
        }
    }

    @Override
    public void searchOn(Optional<Item> optionalItem) {
        if (optionalItem.isPresent()) {
            output.displayTextLines("You examine the bed more closely.",
                    "Yes! That's it! You grab the " + optionalItem.get().getName());
        } else {
            searchFail(Adverb.ON);
        }
    }

    @Override
    public void goTo() {
        output.displayTextLine("You trot over to your bed and curl up in a ball on it.");
    }

}