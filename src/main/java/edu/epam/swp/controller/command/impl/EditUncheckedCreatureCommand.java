package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class EditUncheckedCreatureCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditUncheckedCreatureCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(ParameterName.DESCRIPTION);
        String name = request.getParameter(ParameterName.NAME);
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getId();
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Creature creature = new Creature(id,name,text,date);
        boolean flag;
        try {
            flag = service.editUncheckedCreature(accountId,creature);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            return PagePath.SERVLET_HOME;
        }
        return PagePath.SERVLET_PROFILE + "&id=" + accountId;
    }
}
