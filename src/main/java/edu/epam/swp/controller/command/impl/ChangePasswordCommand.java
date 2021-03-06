package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to change password.
 * @author romab
 */
public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String password = request.getParameter(ParameterName.PASSWORD);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getAccountId();
        boolean flag;
        try {
            flag = service.changePassword(password,accountId);
            if (flag) {
                request.getSession().setAttribute(AttributeName.PASSWORD_CHANGED_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.PASSWORD_CHANGED_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return String.format(PagePath.SERVLET_PROFILE_ID,accountId);
    }
}
