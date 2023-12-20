package prr.clients.tariffplans;

import java.util.TreeMap;
import java.io.Serializable;
import prr.clients.*;
import prr.terminals.*;
import prr.communications.*;

public abstract class TariffPlan implements Serializable {
    private Client _client;
    
    /* This map keeps a record of the previous communications' costs */
    private TreeMap<Communication, Double> _previousCosts = new TreeMap<Communication, Double>();

    public TariffPlan(Client client) {
        _client = client;
    }

    public abstract double getTextCommunicationCost(TextCommunication comm, ClientType type);

    public abstract double getVideoCommunicationCost(VideoCommunication comm, ClientType type);

    public abstract double getVoiceCommunicationCost(VoiceCommunication comm, ClientType type);

    public abstract double applyFriendDiscount(Communication comm, double cost);

    public boolean checkFriendDiscount(Communication comm) {
        Terminal sender = comm.getSender();
        Terminal receiver = comm.getReceiver();

        if (sender.checkFriend(receiver))
            return true;
        return false;
    }
}