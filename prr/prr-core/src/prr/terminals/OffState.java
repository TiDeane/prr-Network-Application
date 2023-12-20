package prr.terminals;

public class OffState extends TerminalState {
    public OffState(Terminal terminal) {
        super(terminal);
    }

    public String status() {
        return "OFF";
    }

    public String changeToIdle() {
        return "O2I";
    }

    public String changeToSilence() {
        return "O2S";
    }
}