package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.User;
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
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        boolean flag;
        String page;
        if (user.getRole().equals(AccountRole.ADMIN)) {
            try {
                flag = service.delete(id);
            } catch (ServiceException e) {
                logger.error("Error occurred while accessing database",e);
                request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            }
            page = PagePath.SERVLET_ADMIN_PAGE;
        } else {
            try {
                long accountId = user.getId();
                flag = service.delete(id,accountId);
            } catch (ServiceException e) {
                logger.error("Error occurred while accessing database",e);
                request.setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            }
            long creatureId = Long.parseLong(request.getParameter(ParameterName.CREATURE));
            request.setAttribute(AttributeName.ID,creatureId);
            page = PagePath.SERVLET_CREATURE;
        }
        return page;
    }
}
