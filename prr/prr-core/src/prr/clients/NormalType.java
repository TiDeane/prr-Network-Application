package prr.clients;

public class NormalType extends ClientType {
    public NormalType(Client client) {
        super(client);
    }

    public String status() {
        return "NORMAL";
    }
}
