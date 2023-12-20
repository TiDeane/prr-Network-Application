package prr.terminals;

import java.io.Serializable;
import java.util.*;

import prr.Network;
import prr.clients.*;
import prr.clients.notifications.*;
import prr.communications.*;
import prr.exceptions.*;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable, Comparable<Terminal> {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

        private Network _network;
        private String _id;
        private String _idClient;
        private List<Terminal> _friends = new ArrayList<Terminal>();
        private List<Terminal> _failedTerminals = new ArrayList<Terminal>();
        private Map<Integer, Communication> _communications = new TreeMap<Integer, Communication>();
        private long _lastInteractiveCommunicationCost = 0;
        private double _payments = 0;
        private double _debts = 0;
        private boolean _unused = true;

        private TerminalState _state;
        private String _previousState = "IDLE";

        public Terminal(String idTerminal, String idClient, Network network) {
                _id = idTerminal;
                _idClient = idClient;
                _network = network;
                _state = new IdleState(this);
        }

        public Terminal(String idTerminal, String idClient, String state, Network network) {
                _id = idTerminal;
                _idClient = idClient;
                _network = network;

                switch(state) {
                case "ON", "IDLE" -> _state = new IdleState(this);
                case "SILENCE" -> _state = new SilenceState(this);
                case "BUSY" ->  _state = new BusyState(this);
                case "OFF" -> _state = new OffState(this);
                }
        }

        public void notifyClients(String notificationType) throws UnknownClientException{
                try {
                        for (Terminal t : _failedTerminals) {
                                _network.getClient(t.getClientId()).addNotification(new AppNotification(this, notificationType));
                        }
                        _failedTerminals.removeAll(_failedTerminals);
                } catch (UnknownClientException e) {}
        }

        public boolean containsSameFailedTerminal(Terminal terminal) {
                for (Terminal t : _failedTerminals) {
                        if(t.getId().equals(terminal.getId()) || t.getClientId().equals(terminal.getClientId()))
                                return true;
                }
                return false;
        }

        public void addFailedTerminals(Terminal terminal) {
                _failedTerminals.add(terminal);
        }

        public void notifyFailedTerminals(String previousState, String state) {
                try {
                        if (previousState.equals("OFF") && state.equals("SILENCE")) {
                                notifyClients("O2S");
                        }
                        else if (previousState.equals("OFF") && state.equals("IDLE")) {
                                notifyClients("O2I");
                                
                        }
                        else if (previousState.equals("SILENCE") && state.equals("IDLE")) {
                                notifyClients("S2I");
                                
                        }
                        else if (previousState.equals("BUSY") && state.equals("IDLE")) {
                                notifyClients("B2I");
                        }
                } catch (UnknownClientException e) {}
        }

        public void setState(TerminalState state) throws TerminalAlreadyInStateException {
                if (_state.status().equals(state.status()))
                        throw new TerminalAlreadyInStateException();
                _previousState = _state.status();
                _state = state;
                notifyFailedTerminals(_previousState, _state.status());
        }

        public TerminalState getState() { return _state; }

        public String getPreviousState() { return _previousState; }

        public String getId() { return _id; }

        public String getClientId() { return _idClient; }

        public long getLastInteractiveCommunicationCost() {
                return _lastInteractiveCommunicationCost;
        }

        public Client getClient() throws UnknownClientException {
                return _network.getClient(_idClient);
        }

        public Communication getCommunication(int commId) {
                return _communications.get(commId);
        }

        /* returns "BASIC" or "FANCY", depending on the Terminal's type */
        public abstract String getType(); 

        public double getPayments() { return _payments; }

        public long getRoundedPayments() {
                long payments = (long) _payments;
                return payments;
        }

        public double getDebts() { return _debts; }

        public long getRoundedDebts() {
                long debts = (long) _debts;
                return debts;
        }

        public double getBalance() {
                return _payments - _debts;
        }

        public void used() {
                _unused = false;
        }

        public boolean checkUnused() {
                return _unused;
        }
        
        public void addFriend(Terminal t) {
                if (t.getId() != _id && !_friends.contains(t))
                        _friends.add(t);
        }

        public void removeFriend(Terminal t) {
                if (_friends.contains(t))
                        _friends.remove(t);
        }

        public boolean checkFriend(Terminal other) {
                String id = other.getId();
                for (Terminal t : _friends)
                        if (t.getId().equals(id))
                                return true;
                return false;
        }

        public Communication searchCommunication(int communicationId) throws InvalidCommunicationException {
                for(int id : _communications.keySet()) {
                        if(communicationId==id) {
                                return _communications.get(id);
                        }
                }
                throw new InvalidCommunicationException();
        }

        public List<Communication> getAllTerminalCommunications() {
                List<Communication> cList = new ArrayList<Communication>();
                cList.addAll(_communications.values());
                return cList;
        }

        public List<Communication> getAllTerminalCommunicationsFromClient() {
                List<Communication> cList = new ArrayList<Communication>();
                for(Communication c : _communications.values()) {
                        if (c.checkSenderId(_idClient)) {
                                cList.add(c);
                        }
                }
                return cList;
        }

        public List<Communication> getAllTerminalCommunicationsToClient() {
                List<Communication> cList = new ArrayList<Communication>();
                for(Communication c : _communications.values()) {
                        if (c.checkReceiverId(_idClient)) {
                                cList.add(c);
                        }
                }
                return cList;
        }

        public Communication getOngoingCommunication() throws NoOngoingCommunicationException {
                for (Communication comm : _communications.values()) {
                        if (comm.status().equals("ONGOING"))
                                return comm;
                }
                throw new NoOngoingCommunicationException();
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive communication) and
         *          it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                try {
                        if (_state.status().equals("BUSY") && getOngoingCommunication().getSender().getId().equals(_id))
                                return true;
                } catch (NoOngoingCommunicationException e) {
                        return false;
                }
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                if (_state.status().equals("BUSY") || _state.status().equals("OFF"))
                        return false;
                return true;
        }

        public void sendTextCommunication(Terminal receiver, String text) throws
                DestinationIsOffException, UnknownClientException {
                if (receiver.getState().status().equals("OFF")) {
                        if (!receiver.containsSameFailedTerminal(this))
                                receiver.addFailedTerminals(this);
                        throw new DestinationIsOffException(receiver.getId());
                }
                int communicationId = _network.getCommunicationId();
                TextCommunication comm = new TextCommunication(communicationId, this, receiver, text);
                _network.increaseCommunicationId();
                _communications.put(communicationId, comm);
                _unused = false;
                receiver.used();


                Client client = getClient();
                double cost = client.getTariffPlan().getTextCommunicationCost(comm, client.getType());
                comm.setCost(cost);
                _debts += cost;
        }

        public void startInteractiveCommunication(Terminal receiver, String type) throws
                        DestinationIsOffException, DestinationIsBusyException,
                        DestinationIsSilentException, UnsupportedAtOriginException,
                        UnsupportedAtDestinationException, TerminalAlreadyInStateException {
                String id = receiver.getId();
                String state = receiver.getState().status();

                if (state.equals("OFF")) {
                        if (!receiver.containsSameFailedTerminal(this))
                                receiver.addFailedTerminals(this);
                        throw new DestinationIsOffException(id);
                }
                else if (state.equals("BUSY")) {
                        if (!receiver.containsSameFailedTerminal(this))
                                receiver.addFailedTerminals(this);
                        throw new DestinationIsBusyException(id);
                }
                else if (state.equals("SILENCE")) {
                        if (!receiver.containsSameFailedTerminal(this))
                                receiver.addFailedTerminals(this);
                        throw new DestinationIsSilentException(id);
                }
                else if (type.equals("VIDEO") && this.getType().equals("BASIC"))
                        throw new UnsupportedAtOriginException(id, type);
                else if (type.equals("VIDEO") && receiver.getType().equals("BASIC"))
                        throw new UnsupportedAtDestinationException(id, type);
                
                int communicationId = _network.getCommunicationId();
                Communication comm;

                /* The only possible cases are "VIDEO" or "VOICE" */
                if (type.equals("VIDEO"))
                        comm = new VideoCommunication(communicationId, this, receiver);
                else /* (type.equals("VOICE")) */
                        comm = new VoiceCommunication(communicationId, this, receiver);

                _network.increaseCommunicationId();
                _communications.put(communicationId, comm);
                _unused = false;
                receiver.used();

                this.setState(new BusyState(this));
                receiver.setState(new BusyState(receiver));
        }

        public void endInteractiveCommunication(int duration) throws UnknownClientException,
                        TerminalAlreadyInStateException, NoOngoingCommunicationException {
                Client client = getClient();
                Terminal receiver;
                String previousReceiverState;
                double cost = 0;

                Communication comm = getOngoingCommunication();
                comm.endCommunication();
                comm.setDuration(duration);

                receiver = comm.getReceiver();
                previousReceiverState = receiver.getPreviousState();

                if (comm.type().equals("VIDEO")) {
                        VideoCommunication videoComm = (VideoCommunication) comm;
                        cost = client.getTariffPlan().getVideoCommunicationCost(videoComm, client.getType());
                }
                else if (comm.type().equals("VOICE")) {
                        VoiceCommunication voiceComm = (VoiceCommunication) comm;
                        cost = client.getTariffPlan().getVoiceCommunicationCost(voiceComm, client.getType());
                }
                comm.setCost(cost);
                _lastInteractiveCommunicationCost = (long) cost;
                _debts += cost;

                if (_previousState.equals("IDLE"))
                        setState(new IdleState(this));
                else if (_previousState.equals("SILENCE"))
                        setState(new SilenceState(this));
                if (previousReceiverState.equals("IDLE"))
                        receiver.setState(new IdleState(receiver));
                else if (previousReceiverState.equals("SILENCE"))
                        receiver.setState(new SilenceState(receiver));
        }

        public void performPayment(Communication comm) throws InvalidCommunicationException {
                if (comm.status().equals("FINISHED")) {
                        comm.payedCommunication();
                        _payments += comm.getCost();
                        _debts -= comm.getCost();
                }
                else
                        throw new InvalidCommunicationException();
        }

        public String friendsToString() {
                String res = "";

                if (_friends.size() > 0) {
                        Collections.sort(_friends);

                        res += "|" + _friends.get(0).getId();
                        if (_friends.size() > 1)
                                for (int i = 1; i < _friends.size(); i++)
                                        res += ("," + _friends.get(i).getId());
                }
                return res;
        }

        @Override
        public int compareTo(Terminal other) {
                return Integer.parseInt(_id) - Integer.parseInt(other.getId());
        }
}