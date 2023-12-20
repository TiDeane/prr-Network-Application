package prr;

import java.io.Serializable;
import java.io.IOException;
import java.io.*;
import java.util.*;

import prr.terminals.*;
import prr.clients.*;
import prr.communications.*;

import prr.exceptions.*;

  /**
   * Class Network implements a network.
   */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

  /**
   * Stores the Network's clients.
   */
	private Map<String, Client> _clients;

  /**
   * Debt comparator used to order clients by descending order of their debts 
   */
	public class DebtComparator implements Comparator<Client>, Serializable {
        @Override
        public int compare(Client c1, Client c2) {
            return (int) (c2.getDebts() - c1.getDebts());
        }
    }

	private Comparator<Client> _debtComparator = new DebtComparator();

  /**
   * The id that will be given to the next communication created
   */
	private int _communicationId = 1;

  /**
   * Initializes the network.
   */
	public Network() {
		_clients = new TreeMap<String, Client>();
	}

	public int getCommunicationId() { return _communicationId; }

	public void increaseCommunicationId() {
		_communicationId++;
	}

  /**
   * Add the client with the given key to the TreeMap.
   *
   * @param key The key of the client to get
   * @param client The client to be added
   */
	public void addClient(String key, Client client) {
		_clients.put(key, client);
	}

  /**
   * Get the terminal with the given id. 
   *
   * @param id The id of the terminal to get
   * @return The terminal associated with the given key
   * @throws UnknownTerminalException if there is no terminal with the given id
   */
	public Terminal searchTerminal(String id) throws UnknownTerminalException {
		List<Client> cList = new ArrayList<Client>();
		cList.addAll(_clients.values());
		for (Client c : cList) {
			Terminal t = c.getTerminal(id);
			if (c.getTerminal(id) != null)
				return t;
		} throw new UnknownTerminalException(id);
	}

  /**
   * Get the client with the given key.
   *  
   * @param key The key of the client to get
   * @return The client associated with the given key
   */
	public Client getClient(String key) throws UnknownClientException {
		if (!clientExists(key))
			throw new UnknownClientException(key);
		return _clients.get(key);
	}

  /**
   * Checks if the terminal with the given id exists.
   *
   * @param id The id of the terminal 
   * @return {boolean} true if the terminal exists, false otherwise.
   */
	public boolean terminalExists(String id) {
		for (Terminal t : getAllTerminals())
			if (t.getId().equals(id))
				return true;
		return false;
	}

  /**
   * Checks if the given terminal id is valid (composed of 6 digits).
   *
   * @param id The id of the terminal 
   * @return {boolean} true if the terminal exists, false otherwise.
   */
	public boolean validTerminalId(String id) {
		if (id.length() == 6 && id.matches("[0-9]+"))	
			return true;
		return false;
	}

  /**
   * Checks if the client with the given key exists.
   *
   * @param key The key of the client whose existence we want to confirm
   * @return true if the client exists, false otherwise
   */
	public boolean clientExists(String key) {
		for (Client c : _clients.values())
			if (c.getKey().equals(key))
				return true;
		return false;
	}

  /**
   * Get all clients registered in the Network.
   *
   * @return a sorted List with all the clients registered in the Network
   */
	public List<Client> getAllClients() {
		List<Client> cList = new ArrayList<Client>();
		cList.addAll(_clients.values());
		Collections.sort(cList);
		return cList;
	}

	public List<Client> getAllClientsWithDebts() {
		List<Client> cList = new ArrayList<Client>();
		for (Client c : _clients.values()) {
			if (c.getDebts() != 0) 
				cList.add(c);
		}
		Collections.sort(cList, _debtComparator);
		return cList;
	}

	public List<Client> getAllClientsWithoutDebts() {
		List<Client> cList = new ArrayList<Client>();
		for (Client c : _clients.values()) {
			if (c.getDebts()==0) 
				cList.add(c);
		}
		Collections.sort(cList);
		return cList;
	}

	public long getGlobalPayments() {
		double payments = 0;
		for (Client c : _clients.values())
			payments += c.getPayments();
		long roundedPayments = (long) payments;
		return roundedPayments;
	}

	public long getGlobalDebts() {
		double debts = 0;
		for (Client c : _clients.values())
			debts += c.getDebts();
		long roundedDebts = (long) debts;
		return roundedDebts;
	}

	public Client getLowestClientPayment() {
		List<Client> _cList = new ArrayList<Client>();
		_cList.addAll(_clients.values());
		Client lowest;
		lowest=_cList.iterator().next();
		for (Client c : _cList) {
			if (lowest.getPayments()>c.getPayments()) {
				lowest=c;
			}
		}
		return lowest;
	}

	public Terminal getMaxTerminalPayment() {
		List<Terminal> _tList = new ArrayList<Terminal>();
		for (Client c : _clients.values()) {
			_tList.add(c.MaxTerminalPayment());
		}
		Terminal max= _tList.iterator().next();
		for ( Terminal t : _tList) {
			if (max.getPayments()<t.getPayments()) {
				max=t;
			}
		}
		return max;		
	}

  /**
   * Get all terminals (from every client) registered in the Network.
   *
   * @return a sorted List with all the terminal registered in the Network
   */
	public List<Terminal> getAllTerminals() {
        List<Terminal> tList = new ArrayList<Terminal>();
        for (Client c : _clients.values())
            tList.addAll(c.getAllClientTerminals());
		Collections.sort(tList);
        return tList;
    }

	public List<Terminal> getAllUnusedTerminals() {
        List<Terminal> tList = new ArrayList<Terminal>();
        for (Client c : _clients.values())
            tList.addAll(c.getAllClientUnusedTerminals());
		Collections.sort(tList);
        return tList;
    }

	public List<Terminal> getAllTerminalsWithPositiveBalance() {
        List<Terminal> tList = new ArrayList<Terminal>();
        for (Client c : _clients.values())
            tList.addAll(c.getAllClientTerminalsWithPositiveBalance());
		Collections.sort(tList);
        return tList;
    }

	public List<Communication> getAllCommunications() {
		List<Terminal> tList = new ArrayList<Terminal>();
		List<Communication> cList = new ArrayList<Communication>();
        for (Client c : _clients.values())
            tList.addAll(c.getAllClientTerminals());
		for (Terminal t : tList)
			cList.addAll(t.getAllTerminalCommunications());
		return cList;
	}

	public List<Communication> getCommunicationsFromClient(String key) throws UnknownClientException {
		if (!clientExists(key))
			throw new UnknownClientException(key);
		List<Terminal> tList = new ArrayList<Terminal>();
		List<Communication> cList = new ArrayList<Communication>();
        for (Client c : _clients.values())
            tList.addAll(c.getAllClientTerminals());
		for (Terminal t : tList)
			cList.addAll(t.getAllTerminalCommunicationsFromClient());
		return cList;
	}

	public List<Communication> getCommunicationsToClient(String key) throws UnknownClientException {
		if (!clientExists(key))
			throw new UnknownClientException(key);
		List<Terminal> tList = new ArrayList<Terminal>();
		List<Communication> cList = new ArrayList<Communication>();
        for (Client c : _clients.values())
            tList.addAll(c.getAllClientTerminals());
		for (Terminal t : tList)
			cList.addAll(t.getAllTerminalCommunicationsToClient());
		return cList;
	}
	
	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws ImportFileException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");
				try {
					registerEntry(fields);
				} catch (UnrecognizedEntryException | UnknownClientException |
						DuplicateClientException | InvalidTerminalException |
						DuplicateTerminalException | UnknownTerminalException e) {
					/* should not happen */
					e.printStackTrace();
				}
			}
        } catch (IOException e1) {
			throw new ImportFileException(filename);
		}
	}

  /**
   * Registers a new entry in this network.
   *
   * @param fields The fields of the entry to import, that were split by the
   *               separator
   * @throws UnrecognizedEntryException if the first parameter field doesn't
   * 									match what is expected
   * @throws UnknownClientException if an entry requires a client, but the
   *								client key provided doesn't match any
   *								registered client
   * @throws DuplicateClientException if there is an attempt to register a new
   * 								  client with a key that already exists
   * @throws InvalidTerminalException if there is an attempt to register a new
   *								  terminal with a key that is not composed
   * 								  of 6 digits
   * @throws DuplicateTerminalException if there is an attempt to register a new
   * 									terminal with a key that already exists
   * @throws UnknownTerminalException if, when adding terminal friends, a key
   * 								  with no corresponding	terminal is given
   */
	public void registerEntry(String... fields) throws UnrecognizedEntryException,
			UnknownClientException, DuplicateClientException, InvalidTerminalException,
			DuplicateTerminalException, UnknownTerminalException {
		switch (fields[0]) {
		case "CLIENT" -> registerClient(fields);
		case "BASIC", "FANCY" -> registerTerminal(fields);
		case "FRIENDS" -> addTerminalFriends(fields);
		default -> throw new UnrecognizedEntryException(fields[0]);
		};
	}

  /**
   * Register a new client in this network.
   *
   * @param id fields 0 - "CLIENT"; 1 - id; 2 - name; 3 - taxId
   * @throws DuplicateClientException if a client with the given key already exists
   */
	public void registerClient(String... fields) throws DuplicateClientException {
		if (clientExists(fields[1]))
			throw new DuplicateClientException(fields[1]);
		int taxId = Integer.parseInt(fields[3]);

		Client client = new Client(fields[1], fields[2], taxId);
		addClient(fields[1], client);
	}

  /**
   * Register a new client in this network.
   *
   * @param id fields 0 - terminal-type; 1 - idTerminal; 2 - idClient; 3 - state
   * @throws UnknownClientException if there is no client with the given key
   * @throws InvalidTerminalException if the terminal id is not composed of 6 digits
   * @throws DuplicateTerminalException if a terminal with the given key already exists
   */
	public void registerTerminal(String... fields) throws UnknownClientException,
			InvalidTerminalException, DuplicateTerminalException {
		if (!clientExists(fields[2]))
			throw new UnknownClientException(fields[2]);
		if (!validTerminalId(fields[1]))
			throw new InvalidTerminalException(fields[1]);
		if (terminalExists(fields[1]))
			throw new DuplicateTerminalException(fields[1]);
		
		Terminal terminal = switch(fields[0]) {
		case "BASIC" -> new BASIC(fields[1], fields[2], fields[3], this);
		case "FANCY" -> new FANCY(fields[1], fields[2], fields[3], this);
		default -> new BASIC(fields[1], fields[2], fields[3], this); /* should not happen */
		};
		Client client = getClient(fields[2]);
		client.addTerminal(fields[1], terminal);
	}

  /**
   * Adds one or more friends to a given terminal.
   *
   * @param id fields 0 - FRIENDS; 1 - idTerminal; 2 or higher - friendId
   * @throws UnknownTerminalException if there is no Terminal with the given id
   */
	public void addTerminalFriends(String... fields) throws UnknownTerminalException {
		Terminal t = searchTerminal(fields[1]);
		String friendIds[] = fields[2].split(",");
		for (int i = 0; i < friendIds.length; i++)
			t.addFriend(searchTerminal(friendIds[i]));
	}
}