package prr.clients;

public class GoldType extends ClientType {
    public GoldType(Client client) {
        super(client);
    }

    public String status() {
        return "GOLD";
    }
}
