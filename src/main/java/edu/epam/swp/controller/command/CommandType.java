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
    CREATURE(new CreatureCommand()),
    CREATE_REVIEW(new CreateReviewCommand()),
    PROFILE(new ProfileCommand()),
    CHANGE_AVATAR(new ChangeAvatarCommand()),
    CONFIRM_EMAIL(new ConfirmEmailCommand()),
    ADMIN_PANEL(new AdminPanelCommand()),
    BLOCK_USER(new BlockUserCommand()),
    UNBLOCK_USER(new UnblockUserCommand()),
    MAKE_ADMIN(new MakeAdminCommand()),
    EDIT_REVIEW(new EditReviewCommand()),
    DELETE_REVIEW(new DeleteReviewCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
