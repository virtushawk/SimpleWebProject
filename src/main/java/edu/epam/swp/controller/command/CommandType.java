package edu.epam.swp.controller.command;

import edu.epam.swp.controller.command.impl.*;

/**
 * The enum represents command type.
 * @author romab
 */
public enum CommandType {

    HOME(new HomeCommand()),
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
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
    DELETE_REVIEW(new DeleteReviewCommand()),
    CHANGE_IMAGE(new ChangeImageCommand()),
    EDIT_CREATURE(new EditCreatureCommand()),
    DELETE_CREATURE(new DeleteCreatureCommand()),
    APPROVE_CREATURE(new ApproveCreatureCommand()),
    SUGGEST_CORRECTION(new SuggestCorrectionCommand()),
    APPROVE_CORRECTION(new ApproveCorrectionCommand()),
    DELETE_CORRECTION(new DeleteCorrectionCommand()),
    SEARCH(new SearchCommand()),
    EDIT_NAME(new EditNameCommand()),
    EDIT_EMAIL(new EditEmailCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    FORGOT_PASSWORD(new ForgotPasswordCommand()),
    CHANGE_UNCHECKED_IMAGE(new ChangeUncheckedImageCommand()),
    EDIT_UNCHECKED_CREATURE(new EditUncheckedCreatureCommand()),
    EDIT_CORRECTION(new EditCorrectionCommand()),
    DELETE_UNCHECKED_CREATURE(new DeleteUncheckedCreatureCommand()),
    DISAPPROVE_CORRECTION(new DisapproveCorrectionCommand()),
    CHANGE_STATUS(new ChangeStatusCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Returns command.
     * @return Command object.
     */
    public Command getCommand() {
        return command;
    }
}
