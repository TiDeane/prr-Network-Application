package prr.clients;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

import prr.terminals.*;
import prr.clients.notifications.*;
import prr.clients.tariffplans.*;

import prr.exceptions.NotificationsAlreadyEnabledException;
import prr.exceptions.NotificationsAlreadyDisabledException;

public class Client implements Serializable, Comparable<Client> {

    private String _key;
    private String _name;
    private int _taxId;

    private TariffPlan _tariffPlan;
    private double _payments = 0;
    private double _debts = 0;

    private boolean _terminalNotifications = true;

    private TreeMap<String, Terminal> _terminals = new TreeMap<String, Terminal>();
    private List<AppNotification> _notifications = new ArrayList<AppNotification>();

    private ClientType _type;

    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
        _type = new NormalType(this);
        _tariffPlan = new BasePlan(this);
    }

    public void setType(ClientType type) {
        _type = type;
    }

    public ClientType getType() { return _type; }

    public TariffPlan getTariffPlan() { return _tariffPlan; }

    public String getKey() { return _key; }

    public double getPayments() {
        updatePaymentsAndDebts();
        return _payments;
    }

    public long getRoundedPayments() {
        updatePaymentsAndDebts();
        long payments = (long) _payments;
        return payments;
    }

    public double getDebts() {
        updatePaymentsAndDebts();
        return _debts;
    }

    public long getRoundedDebts() {
        updatePaymentsAndDebts();
        long debts = (long) _debts;
        return debts;
    }

    public boolean getNotificationsStatus() { return _terminalNotifications; }

    public Terminal getTerminal(String id) {
        return _terminals.get(id);
    }

    public Collection<Terminal> getAllClientTerminals() {
        return _terminals.values();
    }

    public Collection<Terminal> getAllClientUnusedTerminals() {
        List<Terminal> tList = new ArrayList<Terminal>();
        for (Terminal t : _terminals.values()) {
            if (t.checkUnused()) {
                tList.add(t);
            }
        }
        return tList;
    }

    public Collection<Terminal> getAllClientTerminalsWithPositiveBalance() {
        List<Terminal> tList = new ArrayList<Terminal>();
        for (Terminal t : _terminals.values()) {
            if (t.getBalance()>0) {
                tList.add(t);
            }
        }
        return tList;
    }

    public Collection<AppNotification> getAllClientNotifications() {
        if (getNotificationsStatus())
            return _notifications;
        return null;
    }

    public void cleanNotifications() {
        if (getNotificationsStatus())
            _notifications.removeAll(_notifications);
    }

    public void addTerminal(String id, Terminal terminal) {
        _terminals.put(id, terminal);
    }

    public void updatePaymentsAndDebts() {
        double payments = 0;
        double debts = 0;
        for (Terminal t : _terminals.values()) {
            payments += t.getPayments();
            debts += t.getDebts();
        }
        _payments = payments;
        _debts = debts;
    }

    public double getBalance() {
        updatePaymentsAndDebts();
        return _payments - _debts;
    }

    public void enableNotifications() throws NotificationsAlreadyEnabledException {
        if (_terminalNotifications == false)
            _terminalNotifications = true;
        else
            throw new NotificationsAlreadyEnabledException();
    }

    public void disableNotifications() throws NotificationsAlreadyDisabledException {
        if (_terminalNotifications == true)
            _terminalNotifications = false;
        else
            throw new NotificationsAlreadyDisabledException();
    }


    
    public float getAveragePayments() {
        int sum=0;
        int count=0;
        if (_terminals.values().size()==0)
            return 0;
        for (Terminal t : _terminals.values()) {
            sum+=t.getPayments();
            count+=1;
        }
        return sum/count;
    }

    public void addNotification(AppNotification notification) {
        _notifications.add(notification);
    }

    public String notificationsCheck() {
        return ((_terminalNotifications == true) ? "YES" : "NO");
    }

    @Override
    public String toString() {
        updatePaymentsAndDebts();
        long payments = (long) _payments;
        long debts = (long) _debts;
        return "CLIENT|" + _key + "|" + _name + "|" + _taxId + "|" + _type.status() + "|" +
        notificationsCheck() + "|" + _terminals.size() + "|" + payments + "|" + debts + "|" + getAveragePayments();
    }

    @Override
    public int compareTo(Client other) {
        return _key.compareToIgnoreCase(other.getKey());
    }

    public Terminal MaxTerminalPayment() {
        List<Terminal> _tList = new ArrayList<Terminal>();
        _tList.addAll(_terminals.values());
        Terminal max;
        max = _tList.iterator().next();
        for (Terminal t: _tList) {
            if (max.getPayments()<t.getPayments()) {
                    max=t;
            }
        }
        return max;
}
}
