package edu.epam.swp.controller.filter;

import edu.epam.swp.controller.command.CommandType;
import edu.epam.swp.model.entity.AccountRole;

import java.util.*;

class AccessMap {
    static final Map<AccountRole, List<CommandType>> COMMAND_MAP = new EnumMap<>(AccountRole.class);

    //todo изменить
    static {
        COMMAND_MAP.put(AccountRole.GUEST,Arrays.asList(CommandType.HOME,CommandType.CATALOG,CommandType.PROFILE,CommandType.CREATURE
        ,CommandType.LOGIN,CommandType.REGISTER,CommandType.CONFIRM_EMAIL,CommandType.SEARCH,CommandType.FORGOT_PASSWORD));
        COMMAND_MAP.put(AccountRole.INACTIVE,Arrays.asList(CommandType.HOME,CommandType.CATALOG,CommandType.CHANGE_AVATAR,
                CommandType.CONFIRM_EMAIL,CommandType.CREATURE,CommandType.LOGIN,CommandType.LOGOUT,CommandType.PROFILE,
                CommandType.REGISTER,CommandType.SEARCH,CommandType.FORGOT_PASSWORD));
        COMMAND_MAP.put(AccountRole.ADMIN,Arrays.asList(CommandType.HOME,CommandType.CONFIRM_EMAIL,CommandType.LOGIN,
                CommandType.CATALOG,CommandType.CREATURE,CommandType.REGISTER,CommandType.EDIT_CREATURE,CommandType.PROFILE,
                CommandType.ADMIN_PANEL,CommandType.BLOCK_USER,CommandType.CHANGE_AVATAR,CommandType.CHANGE_IMAGE,
                CommandType.CREATE_CREATURE,CommandType.CREATE_REVIEW,CommandType.DELETE_CREATURE,CommandType.DELETE_REVIEW,
                CommandType.EDIT_REVIEW,CommandType.LOGOUT,CommandType.MAKE_ADMIN,CommandType.UNBLOCK_USER,
                CommandType.APPROVE_CREATURE,CommandType.SUGGEST_CORRECTION,CommandType.APPROVE_CORRECTION,CommandType.DELETE_CORRECTION,
                CommandType.SEARCH,CommandType.EDIT_NAME,CommandType.EDIT_EMAIL,CommandType.CHANGE_PASSWORD,CommandType.FORGOT_PASSWORD));
        COMMAND_MAP.put(AccountRole.USER,Arrays.asList(CommandType.HOME,CommandType.CONFIRM_EMAIL,CommandType.LOGIN,
                CommandType.CATALOG,CommandType.CREATURE,CommandType.REGISTER,CommandType.EDIT_CREATURE,CommandType.PROFILE,
                CommandType.CHANGE_AVATAR,CommandType.CREATE_CREATURE,CommandType.CREATE_REVIEW,CommandType.LOGOUT,CommandType.SUGGEST_CORRECTION,
                CommandType.SEARCH,CommandType.EDIT_NAME,CommandType.EDIT_EMAIL,CommandType.CHANGE_PASSWORD,CommandType.FORGOT_PASSWORD,
                CommandType.EDIT_REVIEW,CommandType.DELETE_REVIEW,CommandType.CHANGE_UNCHECKED_IMAGE,CommandType.EDIT_UNCHECKED_CREATURE
                ,CommandType.EDIT_CORRECTION,CommandType.DELETE_CORRECTION,CommandType.DELETE_UNCHECKED_CREATURE));
    }

    private AccessMap() {}
}
