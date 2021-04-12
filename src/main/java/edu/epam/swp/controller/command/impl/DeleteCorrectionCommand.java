package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.CorrectionService;
import edu.epam.swp.model.service.impl.CorrectionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to delete correction
 * @author romab
 */
public class DeleteCorrectionCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteCorrectionCommand.class);
    private static final CorrectionService service = CorrectionServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long accountId = user.getAccountId();
        boolean flag;
        String page;
        try {
            flag = service.delete(accountId,id);
            if(flag) {
                request.getSession().setAttribute(AttributeName.CORRECTION_DELETE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.CORRECTION_DELETE_ERROR,true);
            }
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        page = String.format(PagePath.SERVLET_PROFILE_ID,accountId);
        return page;
    }
}
