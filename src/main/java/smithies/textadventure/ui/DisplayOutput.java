package smithies.textadventure.ui;

public interface DisplayOutput {
    void displayTextLine(String line);

    void displayTextLines(String... lines);

    void displayClosedDoorResponse();
}
