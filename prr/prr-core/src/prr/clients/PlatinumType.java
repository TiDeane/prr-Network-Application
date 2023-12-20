package prr.clients;

public class PlatinumType extends ClientType {
    public PlatinumType(Client client) {
        super(client);
    }

    public String status() {
        return "PLATINUM";
    }
}
