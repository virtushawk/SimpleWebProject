package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HomeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(HomeCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        try {
            List<Creature> creatures = service.findNewCreatures();
            request.setAttribute(AttributeName.HOME_CREATURE_LIST,creatures);
        } catch (ServiceException e) {
            logger.error("Error occurred while connecting to database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
        }
        String page = PagePath.HOME;
        return page;
    }
}
