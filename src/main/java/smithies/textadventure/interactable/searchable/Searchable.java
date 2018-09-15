package smithies.textadventure.interactable.searchable;

import smithies.textadventure.command.Adverb;
import smithies.textadventure.item.Item;

import java.util.Optional;

public interface Searchable {

    Optional<Item> searchAndResolve(Adverb adverb);

    Optional<Item> search(Adverb adverb);
}
