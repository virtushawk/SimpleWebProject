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
 * Command to confirm email.
 * @author romab
 */
public class ConfirmEmailCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ConfirmEmailCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String confirmationKey = request.getParameter(ParameterName.KEY);
        String page;
        boolean flag;
        try {
            flag = service.confirmEmail(confirmationKey);
            if (flag) {
                request.setAttribute(AttributeName.EMAIL_CONFIRMED_MESSAGE,true);
                request.getSession().invalidate();
            } else {
                request.setAttribute(AttributeName.GENERAL_ERROR_MESSAGE,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = PagePath.SERVLET_HOME;
        return page;
    }
}
