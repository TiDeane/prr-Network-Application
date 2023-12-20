package prr.terminals;

import prr.Network;

public class FANCY extends Terminal {
    public FANCY(String idTerminal, String idClient, Network network) {
        super(idTerminal, idClient, network);
    }

    public FANCY(String idTerminal, String idClient, String state, Network network) {
        super(idTerminal, idClient, state, network);
    }

    public String getType() { return "FANCY"; }

    @Override
    public String toString() {
        return "FANCY" + "|" + getId() + "|" + getClientId() + "|" + getState().status() +
            "|" + getRoundedPayments() + "|" + getRoundedDebts() + friendsToString();
    }
}
