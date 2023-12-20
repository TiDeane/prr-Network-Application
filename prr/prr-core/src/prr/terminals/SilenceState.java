package prr.terminals;

public class SilenceState extends TerminalState {
    public SilenceState(Terminal terminal) {
        super(terminal);
    }

    public String status() {
        return "SILENCE";
    }

    public String changeToIdle() {
        return "S2I";
    }
}