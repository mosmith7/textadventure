package smithies.textadventure.command;

import smithies.textadventure.ui.DisplayConsoleOutput;
import smithies.textadventure.ui.DisplayOutput;

import java.util.Optional;

public class CommandCache {

    private DisplayOutput output = new DisplayConsoleOutput();

    private Verb verb;
    private Adverb adverb;
    private Noun noun;

    public void displayQuestionAndRetainVerb(String line, Verb verb) {
        this.verb = verb;
        output.displayTextLine(line);
    }

    public void displayQuestionAndRetainVerbAndAdverb(String line, Verb verb, Adverb adverb) {
        this.adverb = adverb;
        displayQuestionAndRetainVerb(line, verb);
    }

    public void displayQuestionAndRetainVerbAndNoun(String line, Verb verb, Noun noun) {
        this.noun = noun;
        displayQuestionAndRetainVerb(line, verb);
    }

    public Optional<Verb> getCachedVerb() {
        return Optional.ofNullable(this.verb);
    }

    public Optional<Adverb> getCachedAdverb() {
        return Optional.ofNullable(this.adverb);
    }

    public Optional<Noun> getCachedNoun() {
        return Optional.ofNullable(this.noun);
    }

    public void clearCache() {
        this.verb = null;
        this.adverb = null;
        this.noun = null;
    }
}
