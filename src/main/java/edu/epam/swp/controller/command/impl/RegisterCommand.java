package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter(ParameterName.USERNAME);
        String password = request.getParameter(ParameterName.PASSWORD);
        String email = request.getParameter(ParameterName.EMAIL);
        boolean flag;
        try {
            flag = service.registerUser(email,username,password);
        } catch (ServiceException e) {
            logger.error("Error occurred while creating account!",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            return PagePath.REGISTER;
        }
        if (flag) {
            request.setAttribute(AttributeName.REGISTRATION_MESSAGE_CONFIRMED, true);
            return PagePath.SERVLET_HOME;
        } else {
            request.setAttribute(AttributeName.REGISTRATION_MESSAGE_ERROR, true);
            return PagePath.REGISTER;
        }
    }
}
