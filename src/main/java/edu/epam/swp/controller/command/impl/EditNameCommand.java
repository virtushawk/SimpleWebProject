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

public class EditNameCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditNameCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(ParameterName.NAME);
        boolean flag;
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long id = user.getId();
        try {
            flag = service.changeName(name,id);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            return PagePath.HOME;
        }
        return PagePath.SERVLET_PROFILE + "&id=" + user.getId();
    }
}
