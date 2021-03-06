package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.UserStatus;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to change status.
 * @author romab
 */
public class ChangeStatusCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangeStatusCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String accountIdParameter = request.getParameter(ParameterName.ID);
        long accountId = Long.parseLong(accountIdParameter);
        String status = request.getParameter(ParameterName.STATUS);
        UserStatus userStatus = UserStatus.valueOf(status);
        boolean flag;
        try {
            flag = service.changeUserStatus(accountId,userStatus);
            if (flag) {
                request.getSession().setAttribute(AttributeName.STATUS_CHANGE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.STATUS_CHANGE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while requesting the database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return String.format(PagePath.SERVLET_PROFILE_ID,accountId);
    }
}
