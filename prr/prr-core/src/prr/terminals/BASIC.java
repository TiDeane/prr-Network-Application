package prr.terminals;

import prr.Network;

public class BASIC extends Terminal {
    public BASIC(String idTerminal, String idClient, Network network) {
        super(idTerminal, idClient, network);
    }
    
    public BASIC(String idTerminal, String idClient, String state, Network network) {
        super(idTerminal, idClient, state, network);
    }

    public String getType() { return "BASIC"; }

    @Override
    public String toString() {
        return "BASIC" + "|" + getId() + "|" + getClientId() + "|" + getState().status() +
            "|" + getRoundedPayments() + "|" + getRoundedDebts() + friendsToString();
    }
}
