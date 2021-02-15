package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public class EditReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditReviewCommand.class);
    private static final ReviewService service = ReviewServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String text = request.getParameter(ParameterName.REVIEW);
        int rating = Integer.parseInt(request.getParameter(ParameterName.RATING));
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long creatureId = Long.parseLong(request.getParameter(ParameterName.CREATURE));
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        Review review = new Review(id,text,date,rating);
        boolean flag;
        try {
            flag = service.editReview(review);
        } catch (ServiceException e) {
            logger.error("Error occurred while accessing database",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
            return PagePath.SERVLET_HOME;
        }
        if(user.getRole().equals(AccountRole.ADMIN)) {
            return PagePath.SERVLET_ADMIN_PAGE;
        } else {
            return PagePath.SERVLET_CREATURE + "&id=" + creatureId;
        }
    }
}
