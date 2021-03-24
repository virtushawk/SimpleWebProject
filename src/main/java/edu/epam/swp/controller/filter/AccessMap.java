package edu.epam.swp.controller.filter;

import edu.epam.swp.controller.command.CommandType;
import edu.epam.swp.model.entity.AccountRole;

import java.util.*;

/**
 * Class contains a enum map with users roles and their allowed commands.
 * This class is used by AccessFilter class.
 * @see AccessFilter
 * @author romab
 */
class AccessMap {

    static final Map<AccountRole, List<CommandType>> commandMap = new EnumMap<>(AccountRole.class);

    static {
        commandMap.put(AccountRole.GUEST,
                List.of(CommandType.HOME,CommandType.CATALOG,CommandType.PROFILE,CommandType.CREATURE,
                        CommandType.LOGIN,CommandType.REGISTER,CommandType.CONFIRM_EMAIL,CommandType.SEARCH,
                        CommandType.FORGOT_PASSWORD));
        commandMap.put(AccountRole.INACTIVE,
                List.of(CommandType.HOME,CommandType.CATALOG,CommandType.CHANGE_AVATAR,CommandType.CONFIRM_EMAIL,
                        CommandType.CREATURE,CommandType.LOGOUT, CommandType.PROFILE,CommandType.SEARCH));
        commandMap.put(AccountRole.BLOCKED,
                Arrays.asList(CommandType.HOME,CommandType.CATALOG,CommandType.CHANGE_AVATAR,
                        CommandType.CONFIRM_EMAIL,CommandType.CREATURE,CommandType.LOGIN,CommandType.LOGOUT,CommandType.PROFILE,
                        CommandType.REGISTER,CommandType.SEARCH,CommandType.FORGOT_PASSWORD));
        commandMap.put(AccountRole.ADMIN,
                List.of(CommandType.HOME,CommandType.CONFIRM_EMAIL,CommandType.LOGIN,CommandType.CATALOG,CommandType.CREATURE,
                        CommandType.REGISTER,CommandType.EDIT_CREATURE,CommandType.PROFILE,CommandType.ADMIN_PANEL,
                        CommandType.BLOCK_USER,CommandType.CHANGE_AVATAR,CommandType.CHANGE_IMAGE,CommandType.CREATE_CREATURE,
                        CommandType.CREATE_REVIEW,CommandType.DELETE_CREATURE,CommandType.DELETE_REVIEW,CommandType.EDIT_REVIEW,
                        CommandType.LOGOUT,CommandType.MAKE_ADMIN,CommandType.UNBLOCK_USER,CommandType.APPROVE_CREATURE,
                        CommandType.SUGGEST_CORRECTION,CommandType.APPROVE_CORRECTION,CommandType.DELETE_CORRECTION,
                        CommandType.SEARCH,CommandType.EDIT_NAME,CommandType.EDIT_EMAIL,CommandType.CHANGE_PASSWORD,
                        CommandType.FORGOT_PASSWORD,CommandType.DISAPPROVE_CORRECTION,CommandType.CHANGE_STATUS));
        commandMap.put(AccountRole.USER,
                List.of(CommandType.HOME,CommandType.CATALOG,CommandType.CREATURE,CommandType.EDIT_CREATURE,
                        CommandType.PROFILE,CommandType.CHANGE_AVATAR,CommandType.CREATE_CREATURE,CommandType.CREATE_REVIEW,
                        CommandType.LOGOUT,CommandType.SUGGEST_CORRECTION,CommandType.SEARCH,CommandType.EDIT_NAME,
                        CommandType.EDIT_EMAIL,CommandType.CHANGE_PASSWORD,CommandType.EDIT_REVIEW,
                        CommandType.DELETE_REVIEW,CommandType.CHANGE_UNCHECKED_IMAGE,CommandType.EDIT_UNCHECKED_CREATURE,
                        CommandType.EDIT_CORRECTION,CommandType.DELETE_CORRECTION,CommandType.DELETE_UNCHECKED_CREATURE));
    }
    private AccessMap() {}
}
