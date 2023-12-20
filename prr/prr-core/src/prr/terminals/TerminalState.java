package prr.terminals;
import java.io.Serializable;

public abstract class TerminalState implements Serializable {
    private Terminal _terminal;

    public TerminalState(Terminal terminal) {
        _terminal = terminal;
    }

    public Terminal getTerminal() {
        return _terminal;
    }

    public abstract String status();
}