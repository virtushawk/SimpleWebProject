package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to register.
 * @author romab
 */
public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter(ParameterName.USERNAME);
        String password = request.getParameter(ParameterName.PASSWORD);
        String email = request.getParameter(ParameterName.EMAIL);
        long id;
        String page;
        try {
            id = service.registerUser(email,username,password);
            if (id > 0) {
                request.getSession().setAttribute(AttributeName.REGISTRATION_MESSAGE_CONFIRMED, true);
                page = PagePath.SERVLET_HOME;
            } else {
                request.getSession().setAttribute(AttributeName.REGISTRATION_MESSAGE_ERROR, true);
                request.getSession().setAttribute(AttributeName.USERNAME, username);
                request.getSession().setAttribute(AttributeName.PASSWORD, password);
                request.getSession().setAttribute(AttributeName.EMAIL, email);
                page = PagePath.REGISTER;
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while creating account!",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            page = PagePath.REGISTER;
        }
        return page;
    }
}
