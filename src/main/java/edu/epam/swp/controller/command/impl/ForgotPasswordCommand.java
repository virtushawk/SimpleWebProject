package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * command to recover password
 */
public class ForgotPasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ForgotPasswordCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(ParameterName.NAME);
        boolean flag;
        String page;
        try {
            flag = service.restorePassword(name);
            if (flag) {
                request.getSession().setAttribute(AttributeName.EMAIL_CONFIRMATION_MESSAGE, true);
            } else {
                request.getSession().setAttribute(AttributeName.LOGIN_RESTORE_PASSWORD_ERROR, true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = PagePath.LOGIN;
        return page;
    }
}
