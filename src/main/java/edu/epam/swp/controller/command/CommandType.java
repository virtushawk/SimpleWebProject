package edu.epam.swp.controller.command;

import edu.epam.swp.controller.command.impl.*;

public enum CommandType {
    HOME(new HomeCommand()),
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    IMAGE(new ImageCommand()),
    CATALOG(new CatalogCommand()),
    CREATE_CREATURE(new CreateCreatureCommand()),
    CREATURE(new CreatureCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
