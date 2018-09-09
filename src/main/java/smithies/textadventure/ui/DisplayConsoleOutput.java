package smithies.textadventure.ui;

import java.util.Random;

public class DisplayConsoleOutput implements DisplayOutput {

    private Random random = new Random();

    @Override
    public void displayTextLine(String line) {
        System.out.println(line);
    }

    @Override
    public void displayTextLines(String... lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    @Override
    public void displayClosedDoorResponse() {
        int options = 3;
        int option = random.nextInt(options);
        switch (option) {
            case 0:
                displayTextLine("This door is closed. You lie down next to it");
                break;
            case 1:
                displayTextLine("You walk into the door. Ouch. You won't do that again.");
                break;
            case 2:
                displayTextLine("I can't go that way silly.");
                break;
            default:
                displayTextLine("I can't go that way silly.");
                break;

        }

    }

}
