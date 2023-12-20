package prr;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*;

import prr.exceptions.ClientTerminalException;
import prr.exceptions.DuplicateClientException;
import prr.exceptions.InvalidTerminalException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private String _filename = "";

	private Network _network = new Network();
        //FIXME  addmore fields if needed

        public Network getNetwork() {
		return _network;
	}

	/**
	 * @param filename name of the file containing the serialized application's state
         *        to load.
	 * @throws UnavailableFileException if the specified file does not exist or there is
         *         an error while processing this file.
	 */
	public void load(String filename) throws UnavailableFileException {
        _filename = filename;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            _network = (Network) ois.readObject(); 
        }
		catch (IOException | ClassNotFoundException e) {
			throw new UnavailableFileException(_filename);
		}
    }

	/**
         * Saves the serialized application's state into the file associated to the current network.
         *
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
		if (_filename.equals("")) {
			throw new MissingFileAssociationException();
		}
    	try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
      		oos.writeObject(_network);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
         * Saves the serialized application's state into the specified file. The current network is
         * associated to this file.
         *
	 * @param filename the name of the file.
	 * @throws FileNotFoundException if for some reason the file cannot be created or opened.
	 * @throws MissingFileAssociationException if the current network does not have a file.
	 * @throws IOException if there is some error while serializing the state of the network to disk.
	 */
	public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
		_filename=filename;
		save();
	}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 */
	public void importFile(String filename) throws ImportFileException {
        _network.importFile(filename);
	}
}