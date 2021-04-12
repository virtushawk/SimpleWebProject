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
 * Command to remove admin.
 * @author romab
 */
public class RemoveAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RemoveAdminCommand.class);
    private static final UserService service = UserServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        try {
            flag = service.removeAdmin(id);
            if (flag) {
                request.getSession().setAttribute(AttributeName.ACCOUNT_REMOVE_ADMIN_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.ACCOUNT_REMOVE_ADMIN_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while removing the admin",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }
}
