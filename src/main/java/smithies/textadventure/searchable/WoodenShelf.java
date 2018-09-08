package smithies.textadventure.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.command.Noun;
import smithies.textadventure.item.Item;

import java.util.Optional;

public class WoodenShelf extends Searchable {

    private static final Noun WOODEN_SHELF = Noun.WOODEN_SHELF;

    public WoodenShelf(String positionDescription) {
        super(WOODEN_SHELF, positionDescription);
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

}
