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
 * command to unblock user.
 * @author romab
 */
public class UnblockUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UnblockUserCommand.class);
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
            flag = service.unblockUser(id);
            if (flag) {
                request.getSession().setAttribute(AttributeName.ACCOUNT_UNBLOCK_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.ACCOUNT_UNBLOCK_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while unblocking user",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }
}
