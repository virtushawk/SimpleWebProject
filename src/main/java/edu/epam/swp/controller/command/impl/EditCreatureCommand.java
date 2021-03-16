package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Command to edit creature.
 * @author romab
 */
public class EditCreatureCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditCreatureCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(ParameterName.DESCRIPTION);
        String name = request.getParameter(ParameterName.NAME);
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Creature creature = new Creature.CreatureBuilder().withName(name).withDescription(text).withCreatureId(id).
                withLastUpdated(date).build();
        boolean flag;
        try {
            flag = service.editCreature(creature);
            if (flag) {
                request.getSession().setAttribute(AttributeName.CREATURE_EDIT_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.CREATURE_EDIT_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }
}
