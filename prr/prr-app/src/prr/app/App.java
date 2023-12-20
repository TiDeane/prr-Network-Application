package prr.app;

import prr.NetworkManager;
import prr.exceptions.ImportFileException;
import pt.tecnico.uilib.Dialog;

import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.InvalidTerminalException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.DuplicateClientException;

/**
 * Application entry-point.
 */
public class App {

	public static void main(String[] args) {
		try (var ui = Dialog.UI) {
			var receiver = new NetworkManager();

			String datafile = System.getProperty("import");
			if (datafile != null) {
				try {
					receiver.importFile(datafile);
				} 
				catch (ImportFileException e) {
					// no behavior described: just present the problem
					e.printStackTrace();
				}
			}

			(new prr.app.main.Menu(receiver)).open();
		}
	}

}