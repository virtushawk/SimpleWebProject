package edu.epam.swp.controller.command.impl;
import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CreatureCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreatureCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();
    //todo : check and clean
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        Optional<Creature> creature;
        try {
            creature = service.get(id);
        } catch (ServiceException e) {
            logger.error("Error occurred while finding creature",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE, true);
            return PagePath.HOME;
        }
        if (creature.isPresent()) {
            request.setAttribute(AttributeName.CREATURE,creature.get());
            return PagePath.CREATURE;
        } else {
            return PagePath.ERROR;
        }
    }
}
