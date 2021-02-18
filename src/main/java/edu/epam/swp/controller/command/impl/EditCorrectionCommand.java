package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.service.impl.CorrectionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class EditCorrectionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditCorrectionCommand.class);
    private static final CorrectionService service = CorrectionServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        String name = request.getParameter(ParameterName.NAME);
        String description = request.getParameter(ParameterName.DESCRIPTION);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getId();
        Correction correction = new Correction(description,name,date);
        correction.setCorrectionId(id);
        boolean flag;
        try {
            flag = service.editCorrection(accountId,correction);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            return PagePath.SERVLET_HOME;
        }
        return PagePath.SERVLET_PROFILE + "&id=" + accountId;
    }
}
