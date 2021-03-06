package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command to open home page.
 * @author romab
 */
public class HomeCommand implements Command {

    private static final Logger logger = LogManager.getLogger(HomeCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        try {
            int limit = 3;
            List<Creature> newCreatures = service.findNewCreatures(limit);
            List<Creature> popularCreatures = service.findPopularCreatures(limit);
            request.setAttribute(AttributeName.NEW_CREATURE_LIST,newCreatures);
            request.setAttribute(AttributeName.POPULAR_CREATURE_LIST,popularCreatures);
        } catch (ServiceException e) {
            logger.error("Error occurred while connecting to database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
        }
        String page = PagePath.HOME;
        return page;
    }
}
