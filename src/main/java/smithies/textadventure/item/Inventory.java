package smithies.textadventure.item;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private DisplayOutput output = new DisplayConsoleOutput();

    private int limit;
    private List<Item> items = new ArrayList<>();

    public Inventory(int limit) {
        this.limit = limit;
    }

    public boolean isFull() {
        return items.size() >= limit;
    }

    public void view() {
        output.displayTextLine("Inventory: ");
        items.forEach(item -> {
            output.displayTextLine(item.getDescription());
        });
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
