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
 * Command to edit email
 */
public class EditEmailCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditEmailCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(ParameterName.EMAIL);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getAccountId();
        boolean flag;
        try {
            flag = service.changeEmail(email,accountId);
            if (flag) {
                request.getSession().setAttribute(AttributeName.EMAIL_CHANGE_VALID,true);
                user.setEmail(email);
            } else {
                request.getSession().setAttribute(AttributeName.EMAIL_CHANGE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return String.format(PagePath.SERVLET_PROFILE_ID,accountId);
    }
}
