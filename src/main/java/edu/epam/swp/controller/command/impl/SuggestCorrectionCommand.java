package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.service.impl.CorrectionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class SuggestCorrectionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SuggestCorrectionCommand.class);
    private static final CorrectionService service = CorrectionServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long creatureId = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getId();
        String text = request.getParameter(ParameterName.DESCRIPTION);
        String name = request.getParameter(ParameterName.NAME);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Correction correction = new Correction(accountId,creatureId,text,name,date);
        boolean flag;
        try {
            flag = service.create(correction);
        } catch (ServiceException e) {
            logger.error("Error occurred while creating correction",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_CREATURE + "&id=" + creatureId;
    }
}
