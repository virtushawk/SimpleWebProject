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

public class EditEmailCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditEmailCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(ParameterName.EMAIL);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long id = user.getId();
        boolean flag;
        try {
            flag = service.changeEmail(email,id);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            return PagePath.SERVLET_HOME;
        }
        return PagePath.SERVLET_PROFILE + "&id=" + user.getId();
    }
}
