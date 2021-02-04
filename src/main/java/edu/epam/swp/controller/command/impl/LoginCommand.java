package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String password = request.getParameter(ParameterName.PASSWORD);
        String username = request.getParameter(ParameterName.USERNAME);
        Optional<User> optional;
        try {
            optional = service.findUser(username,password);
        } catch (ServiceException e) {
            logger.error("Error occurred while finding user",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            return PagePath.LOGIN;
        }
        if (optional.isPresent()) {
            request.getSession().setAttribute(AttributeName.AUTHORISED,true);
            request.getSession().setAttribute(AttributeName.USER,optional.get());
            return PagePath.SERVLET_HOME;
        } else {
            request.getSession().setAttribute(AttributeName.LOGIN_ERROR_MESSAGE, true);
            return PagePath.LOGIN;
        }
    }
}
