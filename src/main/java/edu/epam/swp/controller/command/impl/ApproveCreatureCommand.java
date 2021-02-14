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

public class ApproveCreatureCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ApproveCreatureCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        try {
            flag = service.approveCreature(id);
        } catch (ServiceException e) {
            logger.error("Error occurred while approving user",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }
}
