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

/**
 * Command to approve correction.
 * @author romab
 */
public class ApproveCorrectionCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ApproveCorrectionCommand.class);
    private static final CorrectionService service = CorrectionServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        try {
            flag = service.approveCorrection(id);
            if (flag) {
                request.getSession().setAttribute(AttributeName.CORRECTION_APPROVE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.CORRECTION_APPROVE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while approving correction",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }
}
