# prr Network Application

IST Object-Oriented Programming project, 2022/2023. The objective of this project was to develop an application for managing a network of communication terminals, called prr. The program allows the registration, management and consultation of clients, terminals and communications.

-------------------

This project utilizes the UI library `po-uilib`, made for the Object-Oriented Programming course at IST university for supporting small interactive applications. **No modifications were made to the `po-uilib` library.**

- **Library:** `po-uilib`
- **License:** [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.en)
- **Latest Version** (as of 2023/12/20): https://web.tecnico.ulisboa.pt/~david.matos/w/pt/images/7/71/Po-uilib-202308010000.tar.bz2

Please refer to the [license terms](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.en) for further details.

-------------------

<!---
Project specification: https://web.tecnico.ulisboa.pt/~david.matos/w/pt/index.php/Programa%C3%A7%C3%A3o_com_Objectos/Projecto_de_Programa%C3%A7%C3%A3o_com_Objectos/Enunciado_do_Projecto_de_2022-2023
-->

Running instructions:
- `$ export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/prr/prr-core/prr-core.jar:$(pwd)/prr/prr-app/prr-app.jar`
- `$ make`
- `$ java prr.app.App`

It is possible to start the application with a text file specified by the Java import property.

The various entities have the formats described below. It is assumed that titles cannot contain the character '|'. There are no malformed entries.  \
Each line follows the following formats:

`CLIENT|id|name|taxId`  \
`terminal-type|idTerminal|idClient|state`  \
`FRIENDS|idTerminal|idTerminal1,...,idTerminalN`

- For clients, `id` is composed of 6 characters, `name` is a character string and `taxID` is composed of 6 digits;
- For terminals, `terminal-type` is either _BASIC_ or _FANCY_, `idTerminal` is composed of 6 digits and `state` is _ON_, _OFF_ or _SILENCE_.

Client definitions always precede terminal definitions. Connections between friends are always after the definition of the remaining entities.
