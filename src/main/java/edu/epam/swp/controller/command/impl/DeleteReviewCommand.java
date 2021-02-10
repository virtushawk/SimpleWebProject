package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteReviewCommand.class);
    private static final ReviewService service = ReviewServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        try {
            flag = service.delete(id);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }
}
