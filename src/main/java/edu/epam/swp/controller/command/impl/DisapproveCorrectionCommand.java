package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.service.impl.CorrectionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DisapproveCorrectionCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DisapproveCorrectionCommand.class);
    private static final CorrectionService service = CorrectionServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        String page;
        try {
            flag = service.delete(id);
            if(flag) {
                request.setAttribute(AttributeName.CORRECTION_DELETE_VALID,true);
            } else {
                request.setAttribute(AttributeName.CORRECTION_DELETE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = PagePath.SERVLET_ADMIN_PAGE;
        return page;
    }
}
