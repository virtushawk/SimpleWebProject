package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to delete creature
 * @author romab
 */
public class DeleteCreatureCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteCreatureCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        String page;
        try {
            flag = service.delete(id);
            if (flag) {
                request.getSession().setAttribute(AttributeName.CREATURE_DELETE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.CREATURE_DELETE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = PagePath.SERVLET_ADMIN_PAGE;
        return page;
    }

}
