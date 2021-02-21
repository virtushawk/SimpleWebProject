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
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getAccountId();
        boolean flag;
        try {
            flag = service.changeName(name,accountId);
            if (flag) {
                request.getSession().setAttribute(AttributeName.NAME_CHANGE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.NAME_CHANGE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return String.format(PagePath.SERVLET_PROFILE_ID,accountId);
    }
}
