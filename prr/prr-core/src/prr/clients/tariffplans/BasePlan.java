package prr.clients.tariffplans;

import prr.clients.*;
import prr.communications.*;

public class BasePlan extends TariffPlan {
    public BasePlan(Client client) {
        super(client);
    }

    public double getTextCommunicationCost(TextCommunication comm, ClientType type) {
        int length = comm.units();
        String stringType = type.status();
        double cost = 0;

        if (stringType.equals("NORMAL")) {
            if (length < 50)
                cost = 10;
            else if (length < 100)
                cost = 16;
            else    /* length >= 100 */
                cost = 2 * length;
        } else if (stringType.equals("GOLD")) {
            if (length < 100)
                cost = 10;
            else    /* length >= 100 */
                cost = 2 * length;
        } else { /* the only other option is "PLATINUM" */
            if (length < 50)
                cost = 0;
            else    /* length >= 50 */
                cost = 4;
        }

        return cost;
    }

    public double getVideoCommunicationCost(VideoCommunication comm, ClientType type) {
        String stringType = type.status();
        int duration = comm.units();
        double cost = 0;

        switch(stringType) {
        case "NORMAL" -> cost = 30 * duration;
        case "GOLD" -> cost = 20 * duration;
        case "PLATINUM" -> cost = 10 * duration;
        default -> cost = 0;
        };

        cost = applyFriendDiscount(comm, cost);
        return cost;
    }

    public double getVoiceCommunicationCost(VoiceCommunication comm, ClientType type) {
        String stringType = type.status();
        int duration = comm.units();
        double cost = 0;

        switch(stringType) {
        case "NORMAL" -> cost = 20 * duration;
        case "GOLD" -> cost = 10 * duration;
        case "PLATINUM" -> cost = 10 * duration;
        default -> cost = 0;
        };

        cost = applyFriendDiscount(comm, cost);
        return cost;
    }

    public double applyFriendDiscount(Communication comm, double cost) {
        if (checkFriendDiscount(comm))
            return cost / 2;
        return cost;
    }
}