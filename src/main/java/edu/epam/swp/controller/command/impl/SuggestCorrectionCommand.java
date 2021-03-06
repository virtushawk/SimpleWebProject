package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.service.impl.CorrectionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Command to suggest correction
 * @author romab
 */
public class SuggestCorrectionCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SuggestCorrectionCommand.class);
    private static final CorrectionService service = CorrectionServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long creatureId = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getAccountId();
        String text = request.getParameter(ParameterName.DESCRIPTION);
        String name = request.getParameter(ParameterName.NAME);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Correction correction = new Correction.CorrectionBuilder().withAccountId(accountId).withCreatureId(creatureId)
                .withText(text).withName(name).withDate(date).build();
        boolean flag;
        String page;
        try {
            flag = service.create(correction);
            if (flag) {
                request.getSession().setAttribute(AttributeName.CORRECTION_MESSAGE_CREATED,true);
            } else {
                request.getSession().setAttribute(AttributeName.CORRECTION_MESSAGE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while creating correction",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = String.format(PagePath.SERVLET_CREATURE_ID,creatureId);
        return page;
    }
}
