package smithies.textadventure.ui;

public class DisplayConsoleOutput implements DisplayOutput {

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

}
