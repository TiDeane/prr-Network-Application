package prr.app.main;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import prr.exceptions.UnavailableFileException;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

	DoOpenFile(NetworkManager receiver) {
		super(Label.OPEN_FILE, receiver);
                addStringField("openFile", Prompt.openFile());
	}

    @Override
    protected final void execute() throws CommandException {
        try {
                _receiver.load(stringField("openFile"));
        } 
        catch (UnavailableFileException e) {
                throw new FileOpenFailedException(e);
        }
    }
}