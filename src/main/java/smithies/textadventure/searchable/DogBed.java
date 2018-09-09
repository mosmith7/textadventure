package smithies.textadventure.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class DogBed extends Searchable {

    private static final Noun DOG_BED = Noun.DOG_BED;

    public DogBed(String positionDescription) {
        super(DOG_BED, positionDescription);
    }

    @Override
    public Optional<Item> tryAndSearch(Adverb adverb) {
        Optional<Item> optionalItem = search(adverb);
        switch (adverb) {
            case UNDER:
                if (optionalItem.isPresent()) {
                    output.displayTextLines("You get your entire head under the soft bed.",
                            "Yes! That's it! You grab the " + optionalItem.get().getName());
                } else {
                    output.displayTextLines("You search under the shelf. Nothing.");
                }
                break;
            case IN:
                if (optionalItem.isPresent()) {
                    output.displayTextLines("You rip open the bed with your teeth, stuff your head in it and have a good rummage.",
                            "Yes! That's it! You grab the " + optionalItem.get().getName());
                } else {
                    output.displayTextLines("You rip open the bed with your teeth, stuff your head in it and have a good rummage.",
                            "Unfortunately you don't find anything.");
                }
                break;
            case ON:
                if (optionalItem.isPresent()) {
                    output.displayTextLines("You examine the bed more closely.",
                            "Yes! That's it! You grab the " + optionalItem.get().getName());
                } else {
                    output.displayTextLines("You search on the bed. Nothing.");
                }
                break;
            default:
                break;
        }

        return optionalItem;
    }

}