package smithies.textadventure.command;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.Optional;

public class CommandCache {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Verb verb;
    private Adverb adverb;

    public void displayQuestionAndRetainVerb(String line, Verb verb) {
        this.verb = verb;
        output.displayTextLine(line);
    }

    public void displayQuestionAndRetainVerbAndAdverb(String line, Verb verb, Adverb adverb) {
        this.adverb = adverb;
        displayQuestionAndRetainVerb(line, verb);
    }

    public Optional<Verb> getCachedVerb() {
        return Optional.ofNullable(this.verb);
    }

    public Optional<Adverb> getCachedAdverb() {
        return Optional.ofNullable(this.adverb);
    }

    public void clearCache() {
        this.verb = null;
        this.adverb = null;
    }
}
