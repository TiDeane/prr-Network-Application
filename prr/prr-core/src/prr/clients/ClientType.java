package prr.clients;
import java.io.Serializable;

public abstract class ClientType implements Serializable {
    private Client _client;
    /* private TariffPlan _tariffPlan; */

    public ClientType(Client client) {
        _client = client;
    }

    public abstract String status();
}